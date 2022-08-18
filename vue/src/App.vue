<template>
  <div id="app">
    <app-header />
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <router-link v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''">Login</router-link><span v-if="$store.state.token == ''">&nbsp;|&nbsp;</span>
      <router-link v-bind:to="{ name: 'register' }" v-if="$store.state.token == ''">Sign Up</router-link>
    </div>
    <router-view />
    <footer>
      <p>© Spothole 2022</p>
    </footer>
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue'

export default {
  components: {
    AppHeader
  }
}
</script>



<style>
#nav {
  text-align: center;
}

a {
  color: #fffffe;
  text-decoration: none;
}

a:hover {
  color: #edd83d;
}

footer {
  text-align: center;
  margin: 40px 0;
}

</style>