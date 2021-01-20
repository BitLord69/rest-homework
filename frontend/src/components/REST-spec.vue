<template>
  <div>
    <h1 class="p-my-2">{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: "REST-spec",
  props: {
    msg: String,
  },
};
</script>

<style scoped lang="scss">
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
