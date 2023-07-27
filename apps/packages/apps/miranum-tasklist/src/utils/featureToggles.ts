const LOCAL_STORAGE_USE_TASKSERVICE_KEY = "FEATURE_USE_TASK_SERVICE";
const COOKIE_USE_TASKSERVICE_KEY = "FEATURE_USE_TASK_SERVICE";
const COOKIE_SHOW_BETA_BUTTON_KEY = "FEATURE_SHOW_BETA_BUTTON";

/**
 * if FEATURE_USE_TASK_SERVICE cookie is set to true, the taskservice will allways used.
 * Otherwise when the FEATURE_SHOW_BETA_BUTTON cookie is set to true and the users activated the feature,
 * the taskservice is used when the current localStorage value is true
 */
export const initShouldUseTaskServiceFeatureToggle = () => {
  const useTaskServiceCookieValue: boolean = getCookie(COOKIE_USE_TASKSERVICE_KEY).trim().toLowerCase() === "true";
  const showBetaButtonCookieValue: boolean = getCookie(COOKIE_SHOW_BETA_BUTTON_KEY).trim().toLowerCase() === "true";

  // if cookie and localStorage values are different, cookie wins
  if (showBetaButtonCookieValue !== shouldUseTaskService()) {
    setShouldUseTaskService(useTaskServiceCookieValue);
  }

  if(!showBetaButtonCookieValue) { // if the user has not the permission to change
    setShouldUseTaskService(useTaskServiceCookieValue);
  }
}
export const shouldUseTaskService = (): boolean => {
  return localStorage.getItem(LOCAL_STORAGE_USE_TASKSERVICE_KEY)?.trim().toLowerCase() === "true"
}

export const setShouldUseTaskService = (newValue: boolean) => {
  setCookie(COOKIE_USE_TASKSERVICE_KEY, newValue ? "true" : "false");
  localStorage.setItem(LOCAL_STORAGE_USE_TASKSERVICE_KEY, newValue ? "true" : "false")
}

export const switchShouldUseTaskService = () => {
  setShouldUseTaskService(!shouldUseTaskService());
}

export const shouldShowBetaButton = (): boolean => {
  return getCookie(COOKIE_SHOW_BETA_BUTTON_KEY).trim().toLowerCase() === "true";
}

/**
 * returns the value of the cookie
 * copied from https://www.w3schools.com/js/js_cookies.asp
 * @param cname
 */
function getCookie(cname: string): string {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(';');
  for (let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function setCookie(cname: string, value: string): void {
  document.cookie = `${cname}=${value}`;
}
