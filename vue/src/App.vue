<template>
  <div id="app">
    <div class="logo">
      <img :src="require('@/assets/img/spothole1.png')" />
    </div>
    <div id="nav" class="nav">
      <div class="button-container nav-button">
        <router-link v-bind:to="{ name: 'home' }">Home</router-link>
      </div>
      <div class="button-container nav-button" v-if="$store.state.token != ''">
        <router-link v-bind:to="{ name: 'logout' }">Logout</router-link>
      </div>
    </div>
    <router-view />
  </div>
</template>

<style scoped>
.nav {
  display: flex;
  justify-content: center;
}
.button-container {
  font-family: 'Montserrat', sans-serif;
  font-weight: bold;
  margin: 1em;
  padding: 1em;
  text-align: center;
  border-radius: 1em;
}
.nav-button {
  background-color: white;

}

a {
  text-decoration: none;
  background-color: white;
}

a:hover {
  color: ;
}
</style>