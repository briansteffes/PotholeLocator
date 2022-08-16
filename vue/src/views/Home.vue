<template>
  <div class="home">
    <h1>Recent Potholes Reported in <br /> Paris, Texas</h1>
    <div class="nav">
      <router-link :to="{ name: 'ViewPothole' }">
        <button type="button" class="btn btn-primary btn-lg btn-lg">View All Potholes</button>
      </router-link>
      <router-link :to="{ name: 'ReportPothole' }">
        <button type="button" class="btn btn-primary btn-lg btn-lg">Report a Pothole</button>
      </router-link> 
      <router-link :to="{ name: 'SubmitClaim' }">
        <button type="button" class="btn btn-success btn-lg btn-lg">Submit a Damage Claim</button>
      </router-link>
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