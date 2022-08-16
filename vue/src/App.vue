<template>
  <div id="app">
    <app-header />
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
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

</style>