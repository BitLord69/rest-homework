<template>
  <About msg="This is my about page!" />
</template>

<script>
import About from "@/components/About.vue";

export default {
  name: "App",
  components: {
    About,
  },
};
</script>

<style lang="scss">
body {
  box-sizing: border-box;
  background-color: burlywood;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
