import Vue from 'vue';
import Vuex from 'vuex';
import user, {UserState} from './modules/user';
import menu, {MenuState} from "../store/modules/menu";

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export interface RootState {
  userState: UserState;
  menuState: MenuState;
}

export const Vuexstore = new Vuex.Store<RootState>({
  modules: {
    user,
    menu,
  },
  strict: debug
});
export default Vuexstore;
