<template>
  <div id="app">
    <div class="logo">
      <img :src="require('@/assets/img/spothole1.png')" />
    </div>
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>

<style>
.logo img {
  display: block;
  margin-left: auto; 
  margin-right: auto;
  margin-top: 25px;
  margin-bottom: 25px;
  width: 250px;
}
</style>