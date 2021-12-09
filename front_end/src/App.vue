<template>
  <div id="app" style="display: flex; flex-direction: column; height: 100%; justify-content: center; align-content: center;">
    <!-- <img src="./assets/logo.png"> -->
    <HomePage/>
  </div>
</template>

<script>
import HomePage from './components/HomePage'

export default {
  name: 'App',
  components: {
    HomePage
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  height: 100%;
  width: 70%;
  margin: 0% 0%;
}
</style>
