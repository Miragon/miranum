import {inject} from "vue";
import {Store} from "vuex";
import {RootState} from "../store";
import AuthService from "../security/AuthService";

export function useStore(key = null) {
  return inject<Store<RootState>>("store")!;
}


export const useServices = () => {
  return {
    $auth: new AuthService(),
  }
}
