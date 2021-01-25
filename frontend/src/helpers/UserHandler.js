import { ref } from "vue";
import { extFetch } from "./extFetch";

const usernameToCheck = ref(null);
const emailToCheck = ref(null);
const currentUser = ref(null);
const isLoggedIn = ref(false);
const userError = ref(null);
const loginError = ref(null);

export default function UserHandler() {
  // async function getUsername(username) {
  //   try {
  //     usernameToCheck.value = await extFetch("/api/user/check/" + username);
  //     console.log('usernametocheck.value:', usernameToCheck.value);
  //   } catch (e) {
  //     userError.value = e;
  //   }
  // }

  // async function getUsername(username) {
  //   try {
  //     emailToCheck.value = await extFetch("/api/user/check/email/" + username);
  //     console.log("username", emailToCheck.value);
  //   } catch (e) {
  //     userError.value = e;
  //   }
  // }

  const login = async (username, password) => {
    let result = await extFetch('/api/auth/signin/', 'POST', { username, password });
    if (!result) {
      isLoggedIn.value = false;
      currentUser.value = null;
      loginError.value = "Bad username and/or password!"
      console.log("Wrong username/password");
    } else {
      if (result.accessToken) {
        console.log("Storing ", result, ' in local storage!!!');
        localStorage.setItem('user', JSON.stringify(result));
      }
      loginError.value = null;
      isLoggedIn.value = true;
      currentUser.value = result;
      currentUser.value.highestRole = result.roles[result.roles.length - 1];
    }
  };

  function logout() {
    localStorage.removeItem('user');
    isLoggedIn.value = false;
    currentUser.value = null;
    return true;
  }

  async function createUser(form) {
    try {
      await extFetch("/rest/v1/user/", "POST", form);
    } catch (e) {
      userError.value = e;
      return;
    }
    await login(form.username, form.password);
  }

  async function startApp() {
    let result;

    try {
      result = await extFetch("/api/auth/whoami/", "GET", undefined, true);    
      if (result.error || result == false) {
        isLoggedIn.value = false;
        userError.value = result.error;
        return;
      }
      isLoggedIn.value = true;
      currentUser.value = result;
      currentUser.value.highestRole = result.roles[result.roles.length - 1];
    } catch (e) {
      userError.value = e;
      isLoggedIn.value = false;
      return;
    }
  }

  return {
    usernameToCheck,
    // getUsername,
    emailToCheck,
    // getEmail,
    currentUser,
    isLoggedIn,
    loginError,
    userError,
    login,
    logout,
    startApp,
    createUser,
  };
}
