<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <span v-if="isLoggedIn">Logout</span>
    <span v-else>
      <router-link :to="{ name: 'Login'}">Login</router-link> |
      <router-link to="/register">Register</router-link>
    </span>
  </div>
</template>

<script>
import UserHandler from '@/helpers/UserHandler'

export default {
  name: "Header",
  
  async setup() {
    const { isLoggedIn, currentUser, startApp } = UserHandler();

    await startApp();

    return { isLoggedIn, currentUser }
  }
}
</script>

<style lang="scss" scoped>
#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}

</style>