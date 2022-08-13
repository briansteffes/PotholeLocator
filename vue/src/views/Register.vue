<template>
  <div id="register" class="text-center">
    <form class="form-register" v-if="firstPage" @submit.prevent="flipPage">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
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
      <div>
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
        <button class="btn btn-lg btn-primary btn-block" type="submit">
          Continue
        </button>  
      </div>
      <div>
        <router-link :to="{ name: 'login' }">Have an account?</router-link>
      </div>  
    </form>
    <form class="form-register" v-if="!firstPage" @submit.prevent="createAccountInfo">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="accountCreationErrors">
        {{ accountCreationErrorMsg }}
      </div>
      <div>
        <input
          id="first-name"
          class="form-control"
          placeholder="First Name"
          v-model="userAccount.firstName"
          required
        />
      </div>
      <div>
        <input
          id="last-name"
          class="form-control"
          placeholder="Last Name"
          v-model="userAccount.lastName"
          required
        />
      </div>
      <div>
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
          <input
          type="text"
          pattern="[0-9]+"
          maxlength="10"
          id="phone"
          class="form-control"
          placeholder="Phone Number"
          v-model="userAccount.phone"
          v-on:change="isPhoneNumberValid"
          required
        />
      </div>
      <div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
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
      firstPage: true
    };
  },
  methods: {
    isPhoneNumberValid() {
      const phoneRe = /[0-9]+/;
      if (!phoneRe.test(this.userAccount.phone)) {
        this.accountCreationErrors = true;
        this.accountCreationErrorMsg = 'Please enter only 10 digits for your phone number.';
      } else {
        this.accountCreationErrors = false;
        this.accountCreationErrorMsg = 'There were problems creating an account for this user.';
      }
    },
    flipPage() {
      if (this.user.password !== this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        this.register();
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
      this.isPhoneNumberValid();
      if (!this.registrationErrors) {
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
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style></style>
