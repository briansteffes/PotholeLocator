<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1>Please Sign In</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <div class="login">
        <div class="icon">
          <span class="material-symbols-rounded">account_circle</span>
          <input
            type="text"
            id="username"
            class="form-control"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus
          />
        </div>
        <div class="icon">
          <span class="material-symbols-rounded">lock</span>
          <input
            type="password"
            id="password"
            class="form-control"
            placeholder="Password"
            v-model="user.password"
            required
          />
        </div>
      </div>
      <div>
        <router-link :to="{ name: 'register' }">Need an account?</router-link>
      </div>
      <div class="button">
        <button type="submit" class="btn btn-primary btn-lg btn-lg">log in</button>
      </div>
    </form>
  </div>
</template>
 
<script>
import authService from "../services/AuthService";
 
export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;
 
          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });              
    }
  }
};
</script>
 
<style scoped>
#login {
  display: flex;
  flex-direction: column;
  margin-top: 3em;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}

.login {
  padding-top: 2em;
  margin: auto;
  width: 24em;
}
 
.form-control {
  padding-left: 3.25em;
}

.button {
  margin-top: 2em;
}
</style>