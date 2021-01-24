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

  async function logout() {
    try {
      await extFetch("/logout/", "GET");
    } catch (e) {
      userError.value = e;
    }
    currentUser.value = null;
    isLoggedIn.value = false;
  }

  const login = async (username, password) => {
    let result;
    let credentials =`username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`;

    console.log("login, credentials:", credentials);
    
     result = await fetch("/login/", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: credentials,
        redirect: "manual",
      });

      if (result.url.includes("error")) {
        console.log("Wrong username/password");
      } else {
        result = await fetch("/api/whoami");
        currentUser.value = await result.json();
      }
    
// console.log("After trying to log in, result:", result);

//       if (result.error) {
//         loginError.value = result.error;
//         isLoggedIn.value = false;
//         return;
//       }

//       isLoggedIn.value = true;
//       currentUser.value = result;

  // } catch (e) {
    //   loginError.value = e;
    //   isLoggedIn.value = false;
    //   return;
    // }
  };

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
      result = await extFetch("/api/whoami/");    
      console.log("whoami - result:", result);      
      if (result.error || result == false) {
        isLoggedIn.value = false;
        userError.value = result.error;
        return;
      }
      isLoggedIn.value = true;
      currentUser.value = result;
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
