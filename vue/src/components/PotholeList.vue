<template>
  <div>
      <br>
      <h2>Search</h2>
      <table id="search">
        <tbody>
            <tr>
                <td>
                    <input type="text" id="potholeNameFilter" v-model="filter.potholeName" placeholder="Name" />
                </td>
                <td>
                    <input type="text" id="potholeLatFilter" v-model="filter.potholeLat" placeholder="Lat" />
                </td>
                <td>
                    <input type="text" id="potholeLongFilter" v-model="filter.potholeLong" placeholder="Long" />
                </td>
            </tr>
            <tr v-for="pothole in filteredList" v-bind:key="pothole.id">
                <td v-bind:id="pothole.id" v-bind:value="pothole.id"  v-on:change="handleCheck($event, pothole.id)" v-bind:checked="selectedPotholeIDs.includes(pothole.id)">{{ pothole.potholeName }}</td>
                <td>{{ pothole.potholeLat }}</td>
                <td>{{ pothole.potholeLong }}</td>
            </tr>
        </tbody>
      </table>
      <br>
      <div v-for="pothole in this.$store.state.potholes" v-bind:key="pothole.id">
            <h2>{{pothole.potholeName}}</h2>
            <p>Lat: {{pothole.potholeLat}}</p>
            <p>Long: {{pothole.potholeLong}}</p>
            <p>Account ID: {{pothole.accountId}}</p>
            <p>Image ID: {{pothole.imageId}}</p>
            <p>Active: {{pothole.active}}</p>
            <p>Reported On: {{formatTime(pothole.uploadTime)}}</p>
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

*{
    color: #fffffe;
}

</style>