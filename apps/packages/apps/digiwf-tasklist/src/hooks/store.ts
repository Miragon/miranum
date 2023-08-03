import {inject} from "vue";
import {Store} from "vuex";
import {RootState} from "../store";
import AuthService from "../security/AuthService";

export function useStore(key = null) {
  return inject<Store<RootState>>("store")!;
}

const authService = new AuthService();

export const useServices = () => {
  return {
    $auth: authService
  }
}
