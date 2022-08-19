<template>
  <div id="pothole-list">
    <div id="container">
      <div id="mapContainer">
        <l-map :zoom="13" :center=this.map_center>
          <l-tile-layer :url="this.tile_layer_url"
                        :attribution="this.tile_layer_attribution"
                        :options="{tileSize:this.tile_layer_tileSize, zoomOffset:this.tile_layer_zoomOffset}">
          </l-tile-layer>
          <div v-for="pothole in filteredList" v-bind:key="pothole.potholeId">
            <l-circle-marker :lat-lng="[pothole.potholeLat, pothole.potholeLong]">
              <l-popup><app-header></app-header></l-popup>
            </l-circle-marker>
          </div>
        </l-map>
      </div>
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
import L from "leaflet";
import AppHeader from "./AppHeader";

// import PotholeDetails from '@/components/PotholeDetails';

export default {
    name: "pothole-list",
    components: {
      AppHeader
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
            tile_layer_url: "https://api.mapbox.com/styles/v1/mapbox/dark-v10/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoiYnJhaW4tc3RlZmZlcyIsImEiOiJjbDZxb2I4ZGEwZm1iM3FweTR2eTI0a2pmIn0.Ypd_EaTjrLDBEifw3QL1YQ",
            tile_layer_attribution: 'Map data (c) <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery (c) <a href="https://www.mapbox.com/">Mapbox</a>',
            tile_layer_maxZoom: 18,
            tile_layer_style: "mapbox/dark-v10",
            tile_layer_zoomOffset: -1,
            tile_layer_tileSize: 512,
            tile_layer_accessToken: "pk.eyJ1IjoiYnJhaW4tc3RlZmZlcyIsImEiOiJjbDZxb2I4ZGEwZm1iM3FweTR2eTI0a2pmIn0.Ypd_EaTjrLDBEifw3QL1YQ",
            map_center: [33.66099201430402, -95.5567548693612],
            circle: {
              center: [33.66099201430402, -95.5567548693612],
              radius: 4500,
              color: 'red'
            },
            potHoleIcon: L.icon({
              iconURL: "https://www.flaticon.com/free-icons/location",
              iconSize: [40,100],
              iconAnchor: [20,100]
            })
        };
    },
    methods: {
    //   addMarker(e) {
    //     const newMarker = new L.Marker(e.latLng)
    //     // newMarker.addTo(map);
    //     console.log(newMarker)
    //   },
    //   scrollTo(refName) {
    //     const element = this.$refs[refName];
    //     const top = element.offsetTop;

    //     window.scrollTo(0, top);
    //   },
      getImgUrl(selectedPotholeId) {
          if (selectedPotholeId < 23) {
              return this.$store.state.images[selectedPotholeId];
          }
          return this.$store.state.images[1];
      },
      checkCredentials() {
          if (this.$store.state.user !== {}) {
            for (let authority of this.$store.state.user.authorities) {
                if (authority.name === "ROLE_ADMIN" || authority.name === "ROLE_EMPLOYEE") {
                    this.hasValidCredentials = true;
                }
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
      setupLeafletMap: function () {},
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
    height: 60vh;
    border-radius: 20px;
}

</style>