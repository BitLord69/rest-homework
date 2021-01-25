<template>
  <div>
  </div>
</template>

<script>
import UserHandler from '@/helpers/UserHandler';
import { useRouter } from 'vue-router';

export default {
  name: 'LO2',

  setup() {
    const router = useRouter();
    const { logout } = UserHandler();

    logout();
    router.push("/");

    return  {  }
  }
}
</script>
