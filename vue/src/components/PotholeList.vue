<template>
  <div id="pothole-list">
    <br>
    <h2>Filter</h2>
    <div id="filter">
      <input type="text" id="potholeNameFilter" v-model="filter.potholeName" placeholder="Name" />
      <input type="text" id="potholeLatFilter" v-model="filter.potholeLat" placeholder="Lat" />
      <input type="text" id="potholeLongFilter" v-model="filter.potholeLong" placeholder="Long" />
    </div>
    <div id="container">
      <div id="mapContainer"></div>
    </div>
    <div id="pothole-container">
        <div v-for="pothole in filteredList" v-bind:key="pothole.potholeId">
            <div class="pothole-info">
                <p>{{ pothole.active === true ? 'Active' : 'Inactive'}}</p>
                <h2>{{pothole.potholeName}}</h2>
                <table class="pothole-table">
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Lat:</p></td><td><p>{{pothole.potholeLat}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Long:</p></td><td><p>{{pothole.potholeLong}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                                        <tr>
                        <td class="right-align"><p>Category:</p></td><td><p>{{pothole.category}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Status:</p></td><td><p>{{pothole.status}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Reported on:</p></td><td><p>{{formatTime(pothole.uploadTime)}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="right-align"><p>Reported by:</p></td><td><p>{{pothole.username}}</p></td>
                    </tr>
                </table>
                <div class="pothole-button-container">
                    <button class="btn btn-primary">Edit</button>
                    <button class="btn btn-primary" @click.prevent="deletePothole(pothole)">Delete</button>
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
            selectedPotholeIDs: [],
            filter: {
                potholeName: '',
                potholeLat: '',
                potholeLong: ''
            },
            map_center: [33.66099201430402, -95.5567548693612]
        };
    },
    methods: {
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
                    this.$store.commit("SET_POTHOLES", response.data);
                    this.isLoading = false;
                })
                .catch(error => {
                    if (error.response && error.response.status === 404) {
                        alert("Pothole details not available.");
                        this.$router.push({ name: 'Home' });
                    }
                });
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
        console.log(this.$store.state.potholes);
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
            return filteredPotholes;
        }
    }
}

</script>

<style scoped>

h2, p {
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

#filter {
    display: flex;
    flex-wrap: wrap;
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
}

.pothole-info img {
    margin: 10px 0 20px 0;
    object-fit: cover;
    width: 200px;
    border-radius: 3px;
}

#pothole-container {
    margin-top: 30px;
    display: flex;
    flex-wrap: wrap;
}

.pothole-button-container {
    margin-bottom: 20px;
    display: flex;
    justify-content: space-evenly;
}

.pothole-button-container button {
    width: 40%;
}

hr {
    margin: 0;
}

.right-align {
    text-align: right;
}

#mapContainer {
  width: 80vw;
  height: 100vh;
}

</style>