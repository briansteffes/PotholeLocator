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
            <div class="pothole-info" v-if="checkForActiveAndCredentials(pothole.active)">
                <div v-if="activeId === pothole.potholeId">
                    <select name="active" v-model="pothole.active">
                        <option value="true">Active</option>
                        <option value="false">Inactive</option>
                    </select>
                </div>
                <p v-if="hasValidCredentials && inactiveId !== pothole.potholeId ">{{ pothole.active === true ? 'Active' : 'Inactive'}}</p>
                <input type="text" v-if="activeId === pothole.potholeId" v-model="pothole.potholeName" placeholder="">
                <h2 v-if="inactiveId !== pothole.potholeId">{{pothole.potholeName}}</h2>
                <img v-bind:src="getImgUrl(pothole.potholeId)">
                <p class="validation-error" v-if="activeId === pothole.potholeId">{{ formErrorMsg }}</p>
                <table class="pothole-table">
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
                    <tr v-if="hasValidCredentials">
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr v-if="hasValidCredentials">
                        <td class="right-align"><p>Reported by:</p></td>
                        <td><p>{{pothole.username}}</p></td>
                    </tr>
                </table>
                <div class="pothole-button-container" v-if="hasValidCredentials">
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
            hasValidCredentials: false,
            filter: {
                potholeName: '',
                potholeLat: '',
                potholeLong: '',
                category: '',
                status: ''
            },
            images: {
                1: 'https://assets.dmagstatic.com/wp-content/uploads/2015/03/texas-pothole.jpg',
                2: 'https://www.cityworks.com/wp-content/uploads/2020/01/pot-hole-blog.gif',
                3: 'https://asphaltpavingdallastx.com/wp-content/uploads/2021/02/pothole-repair-dallas-5-1.jpg',
                4: 'https://www.grangeinsurance.com/-/media/images/tips/grange/potholes1200x630.jpg',
                5: 'https://lichfieldlive.co.uk/wp-content/uploads/2020/03/pothole-1200x900.jpg',
                6: 'https://ewscripps.brightspotcdn.com/dims4/default/d7c67e1/2147483647/strip/true/crop/855x481+7+0/resize/1280x720!/quality/90/?url=http%3A%2F%2Fewscripps-brightspot.s3.amazonaws.com%2F03%2F43%2F1013a15e4ee5929448b4ca4dcb4d%2Fpothole.jpg',
                7: 'https://www.abc27.com/wp-content/uploads/sites/55/2021/03/potholes-formation-pkg.00_00_01_08.Still001.jpg',
                8: 'https://pavingfinder.com/wp-content/uploads/2021/02/pothole-782x423.jpg',
                9: 'https://www.grangeinsurance.com/-/media/images/tips/grange/potholes1200x630.jpg',
                10: 'https://www.lonestarpavingtx.com/wp-content/uploads/2016/07/large-pothole-in-road-.jpg',
                11: 'https://www.blisstowingservice.com/wp-content/uploads/2021/11/road-terrible-condition-with-many-potholes-bumps-car-moving-along-it-1200x675.jpg',
                12: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjlWqGZuDo2sRqFrrN03b793OFuMib2hQfRw&usqp=CAU',
                13: 'https://www.abc27.com/wp-content/uploads/sites/55/2015/03/potholefile_39108805_ver1.0.jpg',
                14: 'https://s.yimg.com/ny/api/res/1.2/fr9BK7gvgISnaUbRmG0VQg--/YXBwaWQ9aGlnaGxhbmRlcjt3PTY0MDtoPTM1OA--/https://s.yimg.com/uu/api/res/1.2/Mb5avk8t48phNZYnRUsp7Q--~B/aD0yMTA1O3c9Mzc2MTthcHBpZD15dGFjaHlvbg--/https://media.zenfs.com/en/las-cruces-sun-news/328ca9d885e2921969ac1d1834d677b5',
                15: 'http://content.cbs19.tv/photo/2015/03/19/7188518_G_3713725_ver1.0.jpg',
                16: 'https://cdn-ds.com/blogs-media/sites/618/2017/05/27205602/pothole-on-road-miami-lakes-automall.jpg',
                17: 'https://arc-anglerfish-arc2-prod-gmg.s3.amazonaws.com/public/FRBMRSIHBJAGZOZL5VXVKP42TM.jpg',
                18: 'https://www.colonyoneauto.com/wp-content/uploads/2020/03/shutterstock_577628641.jpg',
                19: 'https://townsquare.media/site/466/files/2022/03/attachment-Potholes.jpg?w=980&q=75',
                20: 'https://cdn.vox-cdn.com/thumbor/szCfjxJMWn95CNAvRFPOipkh7_A=/0x0:1920x1080/1200x0/filters:focal(0x0:1920x1080):no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/12865897/01_DOTV8131000H_Paving_for_Pizza_30_1920x1080x1_vdf16.1001.jpg',
                21: 'https://st2.depositphotos.com/1002626/8385/i/600/depositphotos_83858840-stock-photo-road-with-potholes-and-puddles.jpg',
                22: 'https://9b16f79ca967fd0708d1-2713572fef44aa49ec323e813b06d2d9.ssl.cf2.rackcdn.com/1140x_a10-7_cTC/20200212emPotholes02-1-1582068901.jpg'
            },
            map_center: [33.66099201430402, -95.5567548693612]
        };
    },
    methods: {
        getImgUrl(selectedPotholeId) {
            if (selectedPotholeId < 23) {
                return this.images[selectedPotholeId];
            }
            return this.images[1];
        },
        checkCredentials() {
            // || authority.name === "ROLE_USER"
            for (let authority of this.$store.state.user.authorities) {
                if (authority.name === "ROLE_ADMIN" || authority.name === "ROLE_EMPLOYEE" || authority.name === "ROLE_USER") {
                    this.hasValidCredentials = true;
                }
            }
        },
        checkForActiveAndCredentials(isSelectedPotholeActive) {
            if (this.hasValidCredentials === false) {
                if (isSelectedPotholeActive === false) {
                    return false;
                }
            }
            return true;
        },
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
        this.checkCredentials();
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
    margin-bottom: 40px;
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
    margin: 10px 0 10px 0;
    object-fit: cover;
    width: 100%;
    height: 200px;
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