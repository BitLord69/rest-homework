<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <span v-if="isLoggedIn">
      <router-link to="/logmeout">Logout</router-link>
    </span>
    <span v-if="!isLoggedIn">
      <router-link :to="{ name: 'Login'}">Login</router-link> |
      <router-link to="/register">Register</router-link>
    </span>
  </div>
  <div v-if="currentUser"><strong>Current user: {{ currentUser.username }}</strong> [{{ currentUser.highestRole.name }}]</div>
  <div v-else><strong>Not logged in</strong></div>
</template>

<script>
import UserHandler from '@/helpers/UserHandler'

export default {
  name: "Header",
  
  async setup() {
    const { isLoggedIn, currentUser, startApp, logout } = UserHandler();

    await startApp();

    return { logout, isLoggedIn, currentUser }
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