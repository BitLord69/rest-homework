<template>
  <div class="home">
    <RESTSpec msg="Welcome to this awesome REST documentation" />
  </div>
</template>

<script>
import RESTSpec from "@/components/REST-spec.vue";

export default {
  name: "Home",
  components: {
    RESTSpec,
  },
};
</script>
