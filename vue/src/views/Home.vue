<template>
  <div class="home">
    <h1>Recent Potholes Reported in Paris, Texas</h1>
    <div class="nav">
      <div class="button-container menu-button">
        <router-link :to="{ name: 'ViewPothole' }">View All Potholes</router-link>
      </div>
      <div class="button-container menu-button">
        <router-link :to="{ name: 'ReportPothole' }">Report a Pothole</router-link>  
      </div>
      <div class="button-container submit-button">
        <router-link :to="{ name: 'SubmitClaim' }">Submit a Damage Claim</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import accountService from '../services/AccountService';

export default {
  name: "home",
  data() {
    return {
      errorMsg: ""
    };
  },
  methods: {
    handleErrorResponse(error, verb) {
      if (error.response) {
        this.errorMsg = `Error ${verb} card. Response received was ${error.response.statusText}.`;
      } else if (error.request) {
        this.errorMsg = `Error ${verb} card. Server could not be reached.`;
      } else {
        this.errorMsg = `Error ${verb} card. Request could not be created.`;
      }
    }
  },
  created() {
    if (this.$store.state.userAccount.accountId === undefined) {
      accountService.getAccount(this.$store.state.user.id)
      .then(response => {
        if (response.status === 200) {
          this.$store.commit("SET_USER_ACCOUNT", response.data);
        }
      })
      .catch(error => {
        this.handleErrorResponse(error, "adding");
      })
    }
  }
};
</script>

<style scoped>
  .home {
    display: flex;
    flex-direction: column;
    text-align: center;
    align-items: center;
    justify-content: center;
    padding-top: 1em;
  }

  .nav {
    display: flex;
    flex-direction: column;
    padding-top: 3em;
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