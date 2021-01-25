<template>
  <h1 class="p-my-5">Please use your credentials to log in</h1>
  <Card style="width:40%;" class="p-mx-auto p-shadow-5">
    <template #content>
    <form>
      <div class="p-d-flex p-flex-column p-jc-center p-ai-center">
        <div class="p-fluid p-mb-2">
          <div class="p-field p-text-left">
              <label for="username">Username</label>
              <InputText id="username" type="text" v-model="form.username" />
          </div>
          <div class="p-field p-text-left">
              <label for="password">Password</label>
              <Password id="password" v-model="form.password" :feedback="false" />
          </div>
        </div>
        <div v-if="loginError" class="p-mb-3 text-red">{{ loginError }}</div>
        <Button 
          label="Login" 
          type="submit" 
          @click.prevent="doLogin" 
          class="p-button-raised p-button-rounded p-button-raised p-mx-2 btn-login" />
      </div>
    </form>
    </template>
  </Card>
</template>

<script>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import UserHandler from '@/helpers/UserHandler';

export default {
  name: "Login",

  setup() {
    const router = useRouter();
    const { login, isLoggedIn, loginError, startApp } = UserHandler();

    const form = reactive({
      username: '',
      password: ''
    });

    async function doLogin() {
      await login(form.username, form.password);
      await startApp();

      if (isLoggedIn.value) {
        router.push('/');
      }
    }

    return { form, loginError, doLogin }
  }
};
</script>

<style scoped lang="scss">
  .btn-login {
    width: 120px;
    height: 50px;
  }

  .text-red {
    font-size: larger;
    color: red;
  }
</style>