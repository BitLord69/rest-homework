import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import PrimeVue from 'primevue/config';

import 'primeflex/primeflex.css';


createApp(App)
  .use(router)
  .use(PrimeVue)
  .mount('#app')
