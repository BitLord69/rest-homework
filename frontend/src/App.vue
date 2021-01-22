<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </div>
  <router-view />

    <div 
    class="gradient-border myButton" 
    >
    Does this work here???!
  </div>
</template>

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

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
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
