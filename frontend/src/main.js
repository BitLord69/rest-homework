import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import PrimeVue from 'primevue/config';

import Card from 'primevue/card';
import Button from 'primevue/button';
import Divider from 'primevue/divider';
import Password from 'primevue/password';
import InputText from 'primevue/inputtext';
import ScrollPanel from 'primevue/scrollpanel';
import ProgressSpinner from 'primevue/progressspinner';

import 'primevue/resources/themes/saga-blue/theme.css';       //theme
import 'primevue/resources/primevue.min.css';                 //core css
import 'primeicons/primeicons.css';                           //icons
import 'primeflex/primeflex.css';

const app = createApp(App).
      use(router).
      use(PrimeVue, { ripple: true });
         
app.component("Card", Card);     
app.component("Button", Button);     
app.component("Divider", Divider);     
app.component("Password", Password);     
app.component("InputText", InputText);
app.component("ScrollPanel", ScrollPanel);
app.component('ProgressSpinner', ProgressSpinner);

app.mount('#app');
