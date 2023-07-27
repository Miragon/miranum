import {inject} from "vue";
import {Store} from "vuex";
import {RootState} from "../store";

export function useStore(key = null) {
  return inject<Store<RootState>>("store")!;
}
