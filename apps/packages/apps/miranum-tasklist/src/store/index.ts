import Vue from 'vue';
import Vuex from 'vuex';
import user, {UserState} from './modules/user';
import menu, {MenuState} from "../store/modules/menu";
import info, {InfoState} from "../store/modules/info";

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export interface RootState {
  userState: UserState;
  menuState: MenuState;
  infoState: InfoState;
}

export const Vuexstore = new Vuex.Store<RootState>({
  modules: {
    user,
    menu,
    info,
  },
  strict: debug
});
export default Vuexstore;
