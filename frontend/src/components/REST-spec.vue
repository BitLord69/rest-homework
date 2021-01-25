<template>
  <div :key="redrawCount" v-if="setupDone">
    <h1 class="p-my-5">{{ msg }}</h1>
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
            <div>{{ spec.description }}</div>
            <div v-if="spec.parameters">
              <h4 class="p-my-1">Parameters</h4>
              <div class="p-grid p-my-1">
                <div v-for="(p, index) in spec.parameters" :key="index" class="p-offset-1 p-col-11 p-my-1 p-py-0">
                  <span class="p-text-bold">{{ p.name }}</span>: {{ p.type }}
                </div>
              </div>
            </div>
            <div v-if="spec.returns">
              <h4 class="p-my-1">Returns</h4>
              <div class="p-grid p-my-1">
                <div v-for="(r, index) in spec.returns" :key="index" class="p-offset-1 p-col-11 p-my-1 p-py-0">
                  <span class="p-text-bold">{{ r.name }}</span>: {{ r.type }}
                  <span v-if="r.array">
                    <span><br /></span>
                    <span v-for="(a, index2) in r.array" :key="index2" class="p-offset-2 p-col-10 p-my-1 p-py-0">
                      <span class="p-text-bold">{{ a.name }}</span>: {{ a.type }}
                    </span>
                  </span>
                </div>
              </div>
            </div>
            <h4 class="p-mt-1 p-mb-2">Access:&nbsp;
            <i class="far fa-thumbs-up" 
              :style="accessIconColor(spec.access, (currentUser && currentUser.highestRole.name) || 'anonymous')"
              :title="getPermissionTitle(spec.access, null, true)"></i>
            </h4>
            <i class="fas fa-user-slash p-mr-1" :style="accessIconColor(spec.access, 'anonymous')" :title="getPermissionTitle(spec.access, 'anonymous')"></i>
            <i class="fas fa-user-check p-mx-1" :style="accessIconColor(spec.access, 'ROLE_USER')" :title="getPermissionTitle(spec.access, 'ROLE_USER')"></i>
            <i class="fas fa-user-shield p-mx-1" :style="accessIconColor(spec.access, 'ROLE_MODERATOR')" :title="getPermissionTitle(spec.access, 'ROLE_MODERATOR')"></i>
            <i class="fas fa-user-cog p-ml-1" :style="accessIconColor(spec.access, 'ROLE_ADMIN')" :title="getPermissionTitle(spec.access, 'ROLE_ADMIN')"></i>
          </div>
          <div class="p-col-8 top" v-if="spec.exampleURL">
            <ExampleLoader :url='"http://localhost:5001/" + spec.exampleURL'/>
          </div>
          <div v-else class="no-example">
            Sorry, you can't test this endpoint here!
          </div>
        </div>
      </template>
    </Card>
  </div>
</template>

<script>
import apiSpec from './apispec';
import ExampleLoader from './ExampleLoader';
import UserHandler from '@/helpers/UserHandler';
import { watch, ref } from 'vue';

export default {
  name: "REST-spec",
  props: {
    msg: String,
  },
  components: {
    ExampleLoader,
  },

  setup() {
    const setupDone = ref(false);
    const redrawCount = ref(0);
    const { currentUser } = UserHandler();

watch(currentUser, () => {
      redrawCount.value++;
    });

    function getPermissionTitle(access, inLevel, personal = false) {
      const level = personal ? (currentUser.value && currentUser.value.highestRole.name) || 'anonymous' : inLevel;
      let title = personal ? "Sorry, your current privileges won't allow you to access this endpoint!" :
                              'Not allowed for ' + inLevel + ' users!';
      switch (access) {
        case 'anonymous': 
          title = personal ? 'Congratulations, you have enough privileges to access this endpoint!' :
                            'Allowed for ' + inLevel + ' users!';
          break;

        case 'ROLE_USER':
          if (level === 'ROLE_USER' || level === 'ROLE_MODERATOR' || level === 'ROLE_ADMIN') {
            title = personal ? 'Congratulations, you have enough privileges to access this endpoint!' :
                            'Allowed for ' + inLevel + ' users!';
          }
          break;

        case 'ROLE_MODERATOR':
          if (level === 'ROLE_MODERATOR' || level === 'ROLE_ADMIN') {
            title = personal ? 'Congratulations, you have enough privileges to access this endpoint!' :
                            'Allowed for ' + inLevel + ' users!';
          }
          break;

        case 'ROLE_ADMIN':
          if (level === 'ROLE_ADMIN') {
            title = personal ? 'Congratulations, you have enough privileges to access this endpoint!' :
                            'Allowed for ' + inLevel + ' users!';
          }
          break;
      }
      return title; 
    }

    function accessIconColor (access, level) {
      let style = '';
      switch (access) {
        case 'anonymous':
          style = 'color:green;'
          break;

        case 'ROLE_USER':
          style = level === 'ROLE_USER' || level === 'ROLE_MODERATOR' || level === 'ROLE_ADMIN' ? 'color:green;': 'color:red;'
          break;

        case 'ROLE_MODERATOR':
          style = level === 'ROLE_MODERATOR' || level === 'ROLE_ADMIN' ? 'color:green;': 'color:red;'
          break;

        case 'ROLE_ADMIN':
          style = level === 'ROLE_ADMIN' ? 'color:green;': 'color:red;'
          break;
      }
      return style;
    }

    setupDone.value = true;

    return { apiSpec, accessIconColor, getPermissionTitle,  currentUser, redrawCount, setupDone }
  }
};
</script>

<style scoped lang="scss">
.no-example {
  margin: auto;
  font-size: larger;
  color: palevioletred;
}

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
