<template>
  <div id="pothole-list">
    <div id="container">
      <div id="mapContainer"></div>
    </div>
    <h2 id="filter-title">Filter</h2>
    <div id="filter">
      <input type="text" id="potholeNameFilter" v-model="filter.potholeName" placeholder="Name" />
      <input type="text" id="potholeLatFilter" v-model="filter.potholeLat" placeholder="Lat" />
      <input type="text" id="potholeLongFilter" v-model="filter.potholeLong" placeholder="Long" />
      <input type="text" id="potholeCategoryFilter" v-model="filter.category" placeholder="Category" />
      <input type="text" id="potholeStatusFilter" v-model="filter.status" placeholder="Status" />
    </div>
    <div id="pothole-container">
        <div v-for="pothole in filteredList" v-bind:key="pothole.potholeId">
            <div class="pothole-info">
                <div v-if="activeId === pothole.potholeId">
                    <select name="active" v-model="pothole.active">
                        <option value="true">Active</option>
                        <option value="false">Inactive</option>
                    </select>
                </div>
                <p v-if="inactiveId !== pothole.potholeId">{{ pothole.active === true ? 'Active' : 'Inactive'}}</p>
                <input type="text" v-if="activeId === pothole.potholeId" v-model="pothole.potholeName" placeholder="">
                <h2 v-if="inactiveId !== pothole.potholeId">{{pothole.potholeName}}</h2>
                <p class="validation-error" v-if="activeId === pothole.potholeId">{{ formErrorMsg }}</p>
                <table class="pothole-table">
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Lat:</p></td>
                        <td v-if="inactiveId !== pothole.potholeId"><p>{{pothole.potholeLat}}</p></td>
                        <td v-if="activeId === pothole.potholeId"><input type="text" v-model="pothole.potholeLat" v-on:change="latitudeValidation(pothole.potholeLat)"></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Long:</p></td>
                        <td v-if="inactiveId !== pothole.potholeId"><p>{{pothole.potholeLong}}</p></td>
                        <td v-if="activeId === pothole.potholeId"><input type="text" v-model="pothole.potholeLong" v-on:change="longitudeValidation(pothole.potholeLong)"></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Category:</p></td>
                        <td v-if="inactiveId !== pothole.potholeId"><p>{{pothole.category}}</p></td>
                        <td v-if="activeId === pothole.potholeId">
                            <select name="category" v-model="pothole.category">
                                <option value="Major">Major</option>
                                <option value="Minor">Minor</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Status:</p></td>
                        <td v-if="inactiveId !== pothole.potholeId"><p>{{pothole.status}}</p></td>
                        <td v-if="activeId === pothole.potholeId">
                            <select name="status" v-model="pothole.status">
                                <option value="Reported">Reported</option>
                                <option value="Inspected">Inspected</option>
                                <option value="Repaired">Repaired</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Reported on:</p></td>
                        <td><p>{{formatTime(pothole.uploadTime)}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Reported by:</p></td>
                        <td><p>{{pothole.username}}</p></td>
                    </tr>
                </table>
                <div class="pothole-button-container">
                    <button class="btn btn-primary" @click.prevent="activateEditMode(pothole.potholeId)" v-if="inactiveId !== pothole.potholeId">Edit</button>
                    <button class="btn btn-primary" @click.prevent="updatePothole(pothole)" v-if="activeId === pothole.potholeId">Save</button>
                    <button class="btn btn-primary" @click.prevent="deletePothole(pothole)" v-if="inactiveId !== pothole.potholeId">Delete</button>
                    <button class="btn btn-primary" @click.prevent="deactivateEditMode" v-if="activeId === pothole.potholeId">Cancel</button>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import potholeService from '../services/PotholeService';
import "leaflet/dist/leaflet.css"
import L from "leaflet"
// import PotholeDetails from '@/components/PotholeDetails';

export default {
    name: "pothole-list",
    components: {
        // PotholeDetails
    },
    data() {
        return {
            isLoading: true,
            errorMsg: "",
            formErrorMsg: "",
            activeId: 0,
            inactiveId: 0,
            selectedPotholeIDs: [],
            filter: {
                potholeName: '',
                potholeLat: '',
                potholeLong: '',
                category: '',
                status: ''
            },
            map_center: [33.66099201430402, -95.5567548693612]
        };
    },
    methods: {
        latitudeValidation(coordinate) {
            // Source for RegEx below: https://www.regexlib.com/Search.aspx?k=latitude&AspxAutoDetectCookieSupport=1
            const number = /^-?([1-8]?[1-9]|[1-9]0)\.{1}\d{1,6}/;
            if (!number.test(coordinate)) {
                this.formErrorMsg = 'Please enter a valid coordinate.';
            } else {
                this.clearValidationMsg();
            }
        },
        longitudeValidation(coordinate) {
            // Source for RegEx below: https://www.regexlib.com/Search.aspx?k=latitude&AspxAutoDetectCookieSupport=1
            const number = /^-?([1]?[1-7][1-9]|[1]?[1-8][0]|[1-9]?[0-9])\.{1}\d{1,6}/;
            if (!number.test(coordinate)) {
                this.formErrorMsg = 'Please enter a valid coordinate.';
            } else {
                this.clearValidationMsg();
            }
        },
        clearValidationMsg() {
            this.formErrorMsg = '';
        },
        activateEditMode(selectedPothole) {
            this.activeId = selectedPothole;
            this.inactiveId = selectedPothole;
        },
        deactivateEditMode() {
            this.activeId = 0;
            this.inactiveId = 0;
            this.retrievePotholes();
            this.clearValidationMsg();
        },
        setupLeafletMap: function () {
          const mapDiv = L.map("mapContainer").setView(this.map_center, 13);
          L.tileLayer(
            "https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}",
            {
              attribution: 'Map data (c) <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery (c) <a href="https://www.mapbox.com/">Mapbox</a>',
              maxZoom: 18,
              id: "mapbox/streets-v11",
              accessToken: "pk.eyJ1IjoiYnJhaW4tc3RlZmZlcyIsImEiOiJjbDZxb2I4ZGEwZm1iM3FweTR2eTI0a2pmIn0.Ypd_EaTjrLDBEifw3QL1YQ"
            }
          ).addTo(mapDiv)
        },
        formatTime(time) {
            const year = time.substring(0, 4);
            const month = time.substring(5, 7);
            const day = time.substring(8, 10);
            return `${month}-${day}-${year}`;
        }, 
        retrievePotholes() {
            potholeService
                .getAllPotholes()
                .then(response => {
                    this.$store.commit("SET_POTHOLES", response.data.sort((a, b) => a.uploadTime < b.uploadTime ? 1 : -1));
                    this.isLoading = false;
                })
                .catch(error => {
                    if (error.response && error.response.status === 404) {
                        alert("Pothole details not available.");
                        this.$router.push({ name: 'Home' });
                    }
                });
        },
        updatePothole(selectedPothole) {
            if (this.formErrorMsg === '') {
                this.deactivateEditMode();
                potholeService 
                    .updatePothole(selectedPothole)
                    .then(response => {
                        if (response.status === 202) {
                            this.retrievePotholes();
                        }
                    })
                    .catch(error => {
                        alert(`An error occurred. Status code: ${error.response.status}`);
                    });
                }
        },
        deletePothole(selectedPothole) {
            potholeService
                .deletePotholeById(selectedPothole.potholeId)
                .then(response => {
                    if (response.status === 204) {
                        this.retrievePotholes();
                    }
                })
                .catch(error => {
                    alert(`An error occurred. Status code: ${error.response.status}`);
                });
        }
    },
    mounted() {
      this.setupLeafletMap();
    },
    created() {
        this.retrievePotholes();
    },
    computed: {
        filteredList() {
            let filteredPotholes = this.$store.state.potholes;
            if (this.filter.potholeName != "") {
                filteredPotholes = filteredPotholes.filter((pothole) =>
                pothole.potholeName
                    .toLowerCase()
                    .includes(this.filter.potholeName.toLowerCase())
                );
            }
            if (this.filter.potholeLat != "") {
                filteredPotholes = filteredPotholes.filter((pothole) =>
                pothole.potholeLat.toString()
                    .includes(this.filter.potholeLat)
                );
            }
            if (this.filter.potholeLong != "") {
                filteredPotholes = filteredPotholes.filter((pothole) =>
                pothole.potholeLong.toString()
                    .includes(this.filter.potholeLong)
                );
            }
            if (this.filter.category != "") {
                filteredPotholes = filteredPotholes.filter((pothole) =>
                pothole.category
                    .toLowerCase()
                    .includes(this.filter.category.toLowerCase())
                );
            }
            if (this.filter.status != "") {
                filteredPotholes = filteredPotholes.filter((pothole) =>
                pothole.status
                    .toLowerCase()
                    .includes(this.filter.status.toLowerCase())
                );
            }
            return filteredPotholes;
        }
    }
}

</script>

<style scoped>

#filter-title {
    margin-top: 40px;
}

p {
    margin: 0;
}

h2 {
    font-size: 1.6em;
}

input {
    background-color: #fffffe;
    border-radius: 40px;
    margin: .5em;
    padding-left: 15px;
    color: #0a0a0a;
}

select {
    background-color: #fffffe;
    border-radius: 40px;
    padding-left: 5px;
    color: #0a0a0a;
}

::placeholder {
    color: #aaaaaa;
}

#pothole-list {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
    flex-wrap: wrap;
}

.validation-error {
    color: #dc3545;
}

#filter {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}

.pothole-table {
    text-align: left;
    margin: 10px auto 0 auto;
}

.pothole-table td {
    padding: 5px 10px;
}

.pothole-info {
    border: 2px solid #0d6efd;
    border-radius: 20px;
    padding: 1em;
    margin: 1em;
    width: 21em;
}

tr input {
    width: 10em;
    margin: 0;
}

.pothole-info img {
    margin: 10px 0 20px 0;
    object-fit: cover;
    width: 200px;
    border-radius: 3px;
}

td input {
    width: 7em;
}

#pothole-container {
    margin-top: 30px;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    max-width: 80%;
}

.pothole-button-container {
    margin-bottom: 20px;
    display: flex;
    justify-content: space-evenly;
}

.pothole-button-container button {
    margin-top: 20px;
    width: 40%;
}

hr {
    margin: 0;
}

.right-align {
    text-align: right;
    font-weight: bold;
}

#mapContainer {
    margin-top: 20px;
    width: 80vw;
    height: 80vh;
}

</style>