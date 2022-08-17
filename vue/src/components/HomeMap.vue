<template>
  <div id="pothole-list">
    <div id="container">
      <div id="mapContainer"></div>
    </div>
  </div>
</template>

<script>
import potholeService from '../services/PotholeService';
import "leaflet/dist/leaflet.css"
import L from "leaflet"
// import PotholeDetails from '@/components/PotholeDetails';

export default {
    name: "home-map",
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

            map_center: [33.66099201430402, -95.5567548693612]
        };
    },
    methods: {
        getImgUrl(selectedPotholeId) {
            if (selectedPotholeId < 23) {
                return this.$store.state.images[selectedPotholeId];
            }
            return this.$store.state.images[1];
        },
        checkCredentials() {
            for (let authority of this.$store.state.user.authorities) {
                if (authority.name === "ROLE_ADMIN" || authority.name === "ROLE_EMPLOYEE") {
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
                    if (response.data.length > 6) {
                        response.data = response.data.slice(0, 5);
                    }
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
    width: 40vw;
    height: 40vh;
}

</style>