<template>
  <div class="home">
    <h1>Recent Potholes Reported in <br /> Paris, Texas</h1>
    <div>
      <home-map />
      <div class="nav">
        <router-link :to="{ name: 'ViewPothole' }">
          <button type="button" class="btn btn-primary btn-lg btn-lg">View All Potholes</button>
        </router-link>
        <router-link v-if="$store.state.token != ''" :to="{ name: 'ReportPothole' }">
          <button type="button" class="btn btn-success btn-lg btn-lg">Report a Pothole</button>
        </router-link> 
      </div>
      <home-list />
    </div>
  </div>
</template>

<script>
import HomeList from '../components/HomeList.vue';
import HomeMap from '../components/HomeMap.vue';
import accountService from '../services/AccountService';

export default {
  components: { HomeList, HomeMap },
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
  flex-direction: row;
  justify-content: center;
  margin-bottom: 30px;
}

.btn {
  margin: 0 1em 0 1em;
  font-size: 1em;
}

</style>