<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
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
        <div>
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
        <div>
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
      <div>
        <button type="submit" class="btn btn-primary">log in</button>
      </div>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import accountService from '../services/AccountService';

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
            // FIX THIS WITH ERROR HANDLING
            const account = accountService.getAccount(response.data.user.id);
            this.$store.commit("SET_USER_ACCOUNT", account.data);
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

<style>
@import url('https://fonts.googleapis.com/css2?family=Montserrat&family=Racing+Sans+One&display=swap');

* {
  background: #00123d;
}

h1 {
  font-family: Racing Sans One;
  color: #edd83d;
}

.login {
  display: flex;
  flex-direction: column;
  padding-top: 75px;
  margin: auto;
  width: 500px;
}

a {
  font-family: 'Montserrat', sans-serif;
}

.form-control {
  border: 2px solid #5C8DFF;
  font-family: 'Montserrat', sans-serif;
  border-radius: 40px;
  margin-bottom: 25px;
  padding-left: 15px;
}

.btn-primary {
  width: 500px;
  font-family: 'Montserrat', sans-serif;
  font-weight: 900;
  background: #0043e0;
  letter-spacing: 2px;
  border: 2px solid #5C8DFF;
  border-radius: 40px;
  margin-top: 25px;
}

::-webkit-input-placeholder {
   font-style: italic;
}
:-moz-placeholder {
   font-style: italic;  
}
::-moz-placeholder {
   font-style: italic;  
}
:-ms-input-placeholder {  
   font-style: italic; 
}
</style>