<template>
  <div>
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
            errorMsg: ""
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
    // computed() {
    // }
}
</script>

<style>

</style>