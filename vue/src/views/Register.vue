<template>
  <div id="register" class="text-center">
    <form class="form-register" v-if="firstPage">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div>
        <label for="username" class="sr-only">Username</label>
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
        <label for="password" class="sr-only">Password</label>
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />
      </div>
      <div>
        <label for="confirmPassword" class="sr-only">Confirm Password</label>
        <input
          type="password"
          id="confirmPassword"
          class="form-control"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required
        />
      </div>
      <div>
        <button class="btn btn-lg btn-primary btn-block" @click.prevent="flipPage">
          Continue
        </button>  
      </div>
      <div>
        <router-link :to="{ name: 'login' }">Have an account?</router-link>
      </div>  
    </form>
    <form class="form-register" v-if="!firstPage">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="accountCreationErrors">
        {{ accountCreationErrorMsg }}
      </div>
      <div>
        <label for="first-name" class="sr-only">First Name</label>
        <input
          id="first-name"
          class="form-control"
          placeholder="First Name"
          v-model="userAccount.firstName"
          required
        />
      </div>
      <div>
        <label for="last-name" class="sr-only">Last Name</label>
        <input
          id="last-name"
          class="form-control"
          placeholder="Last Name"
          v-model="userAccount.lastName"
          required
        />
      </div>
      <div>
        <label for="email" class="sr-only">Email</label>
        <input
          type="email"
          id="email"
          class="form-control"
          placeholder="Email"
          v-model="userAccount.email"
          required
        />
      </div>
      <div>
        <label for="phone" class="sr-only">Phone Number</label>
          <input
          type="number"
          id="phone"
          class="form-control"
          placeholder="Phone Number"
          v-model="userAccount.phone"
          required
        />
      </div>
      <div>
        <button class="btn btn-lg btn-primary btn-block" @click.prevent="createAccountInfo">
          Submit
        </button>  
      </div>            
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';
import accountService from '../services/AccountService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      userAccount: {
        userId: '',
        firstName: '',
        lastName: '',
        email: '',
        phone: ''
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
      accountCreationErrors: false,
      accountCreationErrorMsg: 'There were problems creating an account for this user.',
      firstPage: true,
      formComplete: false
    };
  },
  methods: {
    checkForm() {
      if (this.user.username !== '' && this.user.password !== '' && this.user.confirmPassword !== '') {
        this.formComplete = true;
      } else {
        this.formComplete = false;
      }
    },
    flipPage() {
      this.checkForm();
      if (this.formComplete === false) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Please fill out all fields.';
      } else {
        if (this.user.password !== this.user.confirmPassword) {
          this.registrationErrors = true;
          this.registrationErrorMsg = 'Password & Confirm Password do not match.';
        } else {
          this.register();
        }
      }
    },
    register() {
      authService
        .register(this.user)
        .then((response) => {
          if (response.status === 201) {
            this.userAccount.userId = response.data.id;
            this.firstPage = false;
          }
          })
        .catch((error) => {
          const response = error.response;
          this.registrationErrors = true;
          if (response.status === 400) {
            this.registrationErrorMsg = 'Bad Request: Validation Errors';
          }
        });
      return true;
    },
    createAccountInfo() {
      accountService
        .createAccount(this.userAccount)
        .then((response) => {
          if (response.status === 201) {
            this.$router.push({
              path: '/login',
              query: { registration: 'success' },
            });
          }
        })
        .catch((error) => {
          const response = error.response;
          this.accountCreationErrors = true;
          if (response.status === 400) {
            this.accountCreationErrorMsg = 'Bad Request: Account Registration Error';
          }
        });
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style></style>
