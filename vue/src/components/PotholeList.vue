<template>
  <div id="pothole-list">
      <br>
      <h2>Filter</h2>
      <div id="filter">
        <input type="text" id="potholeNameFilter" v-model="filter.potholeName" placeholder="Name" />
        <input type="text" id="potholeLatFilter" v-model="filter.potholeLat" placeholder="Lat" />
        <input type="text" id="potholeLongFilter" v-model="filter.potholeLong" placeholder="Long" />
      </div>
    <div id="pothole-container">
        <div v-for="pothole in filteredList" v-bind:key="pothole.potholeName">
            <div class="pothole-info">
                <h2>{{pothole.potholeName}}</h2>
                <img src="https://media.istockphoto.com/photos/pot-hole-picture-id174662203?k=20&m=174662203&s=612x612&w=0&h=pcvejYWQ1S43k-VG4J5x36ikro37hRzQS-Ms7Lmgwkw=">
                <table class="pothole-table">
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td><p>Lat:</p></td><td><p>{{pothole.potholeLat}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td><p>Long:</p></td><td><p>{{pothole.potholeLong}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td><p>Active:</p></td><td><p>{{pothole.active}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                                        <tr>
                        <td><p>Category:</p></td><td><p>{{pothole.category}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td><p>Status:</p></td><td><p>{{pothole.status}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td><p>Reported on:</p></td><td><p>{{formatTime(pothole.uploadTime)}}</p></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td><p>Reported by:</p></td><td><p>{{pothole.username}}</p></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import potholeService from '../services/PotholeService';
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
            }
        };
    },
    methods: {
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
        }
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

input {
    background-color: #fffffe;
    border-radius: 40px;
    margin: .5em;
    padding-left: 15px;
}

input {
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

hr {
    margin: 0;
}

</style>