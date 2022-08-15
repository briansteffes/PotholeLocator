<template>
  <div class="home">
    <h1>Recent Potholes Reported in <br /> Paris, Texas</h1>
    <div class="nav">
      <button type="button" class="btn btn-primary btn-lg btn-lg">
        <router-link :to="{ name: 'ViewPothole' }">View All Potholes</router-link>
      </button>
      <button type="button" class="btn btn-primary btn-lg btn-lg">
        <router-link :to="{ name: 'ReportPothole' }">Report a Pothole</router-link>  
      </button>
      <button type="button" class="btn btn-success btn-lg btn-lg">
        <router-link :to="{ name: 'SubmitClaim' }">Submit a Damage Claim</router-link>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "home"
};
</script>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  margin-top: 3em;
  margin-left: auto;
  margin-right: auto;
}

.nav {
  flex-direction: column;
  margin-left: auto;
  margin-right: auto;
}

.btn {
  margin-top: 2em;
  font-size: 1em;
}

.btn a:hover {
  color: #fefefe;
}

a {
  text-decoration: none;
  background: none;
  color: #fefefe;
}
</style>