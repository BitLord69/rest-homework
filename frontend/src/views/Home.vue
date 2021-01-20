<template>
  <div class="home">
    <RESTSpec msg="Welcome to this awesome REST documentation" />
  </div>
</template>

<script>
// @ is an alias to /src
import RESTSpec from "@/components/REST-spec.vue";

export default {
  name: "Home",
  components: {
    RESTSpec,
  },
};
</script>
