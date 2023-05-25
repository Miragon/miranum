import Vue from 'vue';
import Vuex from 'vuex';
import user, {UserState} from './modules/user';
import processDefinitions, {ProcessDefinitionState} from "../store/modules/processDefinitions";
import processInstances, {ProcessInstancesState} from "../store/modules/processInstances";
import menu, {MenuState} from "../store/modules/menu";
import info, {InfoState} from "../store/modules/info";

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export interface RootState {
  userState: UserState;
  processDefinitionState: ProcessDefinitionState;
  processInstancesState: ProcessInstancesState;
  menuState: MenuState;
  infoState: InfoState;
}

export const Vuexstore = new Vuex.Store<RootState>({
  modules: {
    user,
    processDefinitions,
    processInstances,
    menu,
    info,
  },
  strict: debug
});
export default Vuexstore;
