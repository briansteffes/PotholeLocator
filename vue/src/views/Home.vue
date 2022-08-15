<template>
  <div class="home">
    <h1>Recent Potholes Reported in Paris, Texas</h1>
      <router-link :to="{ name: 'ViewPothole' }"><div class="button-container menu-button">View All Potholes</div></router-link>
      <router-link :to="{ name: 'ReportPothole' }"><div class="button-container menu-button">Report a Pothole</div></router-link>  
      <router-link :to="{ name: 'SubmitClaim' }"><div class="button-container submit-button">Submit a Damage Claim</div></router-link>
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
    text-align: center;
    align-items: center;
    justify-content: center;
  }
  
  .menu-button {
    background-color: #0d6efd;
  }

  a .menu-button {
    color: #fffffe;
    background-color: #0d6efd;
  }

  a {
    text-decoration: none;
  }

  .button-container {
    font-family: 'Montserrat', sans-serif;
    font-weight: bold;
    margin: 1em;
    padding: 1em;
    text-align: center;
    width: 24em;
    border-radius: 3em;
  }

  .submit-button {
    background-color: rgba(64, 249, 155);
  }

  a .submit-button {
    color: #00123d;
    background-color: rgba(64, 249, 155);
  }

</style>