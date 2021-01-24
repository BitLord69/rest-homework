<template>
  <h3>Please use your credentials to log in</h3>
  <form class="p-d-flex p-flex-column p-jc-center p-ai-center p-mx-auto p-mt-5">
    <div class="p-fluid p-mb-2" style="width:30%;">
      <div class="p-field p-text-left">
          <label for="username">Username</label>
          <InputText id="username" type="text" v-model="form.username" />
      </div>
      <div class="p-field p-text-left">
          <label for="password">Password</label>
          <Password id="password" v-model="form.password" :feedback="false" />
      </div>
    </div>
    <Button 
      label="Login" 
      type="submit" 
      @click.prevent="doLogin" 
      class="p-button-raised p-button-rounded p-button-raised p-mx-2 btn-login" />
  </form>
</template>

<script>
import { reactive } from 'vue'
import UserHandler from '@/helpers/UserHandler';

export default {
  name: "Login",

  setup() {
    const { login } = UserHandler();
    const form = reactive({
      username: '',
      password: ''
    });

    async function doLogin() {
      console.log("In doLogin, form:", form);
      await login(form.username, form.password);
    }

    return { form, doLogin }
  }
};
</script>

<style scoped lang="scss">
  .btn-login {
    width: 120px;
    height: 50px;
  }
</style>