<template>
  <div class="p-d-flex p-flex-column p-jc-center">
    <div class="p-mx-auto p-mb-2">
      <Button 
        class="p-button-raised p-button-rounded p-button-raised" 
        label="Try it out!"
        @click="getData">
      </Button>
    </div>
    <div v-if="waitingForData" class="p-d-flex p-flex-column p-jc-center">
      <div class="p-text-center p-my-4">Fetching data, hold on to your horses...</div>
      <ProgressSpinner style="width:60px;height:60px" strokeWidth="8" animationDuration=".5s"/>
    </div>
    <div v-else style="border: 1px solid grey;">
      <div v-if="error" class="p-text-center">
        <h4>{{ error }}</h4>
      </div>
      <div v-if="res">
        <ScrollPanel style="height: 160px;">
          <div v-if="(typeof (res) === 'string')">
            {{ res }}
          </div>
          <div v-else>
            <vue-json-pretty 
              :data='res'
              :show-double-quotes=false
              :show-length=true
              :show-line=true
              :collapsed-on-click-brackets=true
              :custom-value-formatter="customLinkFormatter" />
          </div>
        </ScrollPanel>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { extFetch } from '@/helpers/extFetch';
import VueJsonPretty from 'vue-json-pretty';
import 'vue-json-pretty/lib/styles.css';

export default {
  name: "ExampleLoader",
  props: {
    url: String,
  },
  components: {
    VueJsonPretty,
  },

  setup(props) {
    const res = ref(null);
    const error = ref(null);
    const waitingForData = ref(false);

    async function getData() {
      waitingForData.value = true;
      try {
        res.value = await extFetch(props.url, "GET", undefined, true);
        if (!res.value) {
          error.value = "Error while fetching data..."
        }
      } catch (e) {
        error.value = e
        console.log("error fetching... ", e);
      }
      waitingForData.value = false;
    }

    const customLinkFormatter = (data, key, path, defaultFormatted) => {
      if (typeof data === 'string' && data.startsWith('https://')) {
        return `<a style="color:red;" href="${data}" target="_blank">"${data}"</a>`;
      } else {
        return defaultFormatted;
      }
    };

    return { res, error, waitingForData, getData, customLinkFormatter };
  }
}
</script>

<style scoped lang="scss">
.myButton {
  width: 80px;
  height: 45px;
}
</style>