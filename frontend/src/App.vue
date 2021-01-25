<template>
  <Suspense>
    <Header />
  </Suspense>
  
  <router-view />
</template>

<script>
import Header from '@/components/Header'

export default {
  components: {
    Header
  },

  setup() {
    return {  }
  }
}
</script>

<style lang="scss">
body {
  box-sizing: border-box;
  background-color: cornsilk;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

$borderWidth: 5px;

.myButton {
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--primary-color-text);
  background: var(--primary-color);
  width: 160px;
  height: 70px;
  cursor: pointer;
}

.gradient-border {
  background: var(--primary-color);
  position: relative;
  border-radius: calc(90* #{$borderWidth});
}

.gradient-border:after {
  content: '';
  position: absolute;
  top: calc(-1 * #{$borderWidth});
  left: calc(-1 * #{$borderWidth});
  height: calc(100% + #{$borderWidth} * 2);
  width: calc(100% + #{$borderWidth} * 2);
  background: linear-gradient(60deg, #f79533, #f37055, #ef4e7b, #a166ab, #5073b8, #1098ad, #07b39b, #6fba82);
  border-radius: calc(90 * #{$borderWidth});
  z-index: -1;
  animation: animatedgradient 3s ease alternate infinite;
  background-size: 300% 300%;
}

@keyframes animatedgradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}
</style>
