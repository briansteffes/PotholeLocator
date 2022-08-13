<template>
  <div class="home">
    <h1>Recent Potholes Reported in Paris, Texas</h1>
    <div>
      <router-link :to="{ name: 'ViewPothole' }">View All Potholes</router-link>
    </div>
    <div>
      <router-link :to="{ name: 'ReportPothole' }">Report a Pothole</router-link>  
    </div>
    <div>
      <router-link :to="{ name: 'SubmitClaim' }">Submit a Damage Claim</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "home"
};
</script>
