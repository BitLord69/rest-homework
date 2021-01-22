<template>
  <div>
    <h1 class="p-my-2">{{ msg }}</h1>
  </div>
  <div class="p-grid">
    <Card v-for="(spec, index) in apiSpec.data" :key="index" class="p-col-8 p-offset-2 p-my-2 p-text-left p-shadow-5">
      <template #header>
        <h2 class="p-my-2">Route: <em>{{spec.route}}</em></h2>
      </template>
      <template #content>
        <div class="p-grid parent-top">
          <div class="p-col-4">
            <div>
              <span class="p-text-bold">Method: <em>{{ spec.method }}</em></span>
            </div>
            <div>{{ spec.description}}</div>
            <h4 class="p-my-1">Returns</h4>
            <div class="p-grid p-my-1">
              <div v-for="(r, index) in spec.returns" :key="index" class="p-offset-1 p-col-11 p-my-1 p-py-0">
                <span class="p-text-bold">{{ r.name }}</span>: {{ r.type }}
              </div>
            </div>
          </div>
          <div class="p-col-8 top">
            <ExampleLoader :url='"http://localhost:5001/" + spec.exampleURL'/>
          </div>
        </div>
      </template>
    </Card>
  </div>
</template>

<script>
import apiSpec from './apispec';
import ExampleLoader from './ExampleLoader';

export default {
  name: "REST-spec",
  props: {
    msg: String,
  },
  components: {
    ExampleLoader,
  },

  setup() {
    return { apiSpec }
  }
};
</script>

<style scoped lang="scss">
::v-deep(.p-card-body) {
  padding: 0 !important;
}

.parent-top {
  position: relative;
}

.top {
  position: absolute;
  top: -50px;
  left: 33%;
}

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
