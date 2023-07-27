import {ActionContext} from "vuex";
import {RootState} from "../index";
import {FetchUtils, UserRestControllerApiFactory, UserTO} from '@muenchen/digiwf-engine-api-internal';
import {ApiConfig} from "../../api/ApiConfig";

export interface UserState {
  info: UserTO;
  lastFetch: number | null;
}

export default {
  namespaced: true,
  state: {
    info: {},
    lastFetch: null
  } as UserState,
  getters: {
    shouldUpdate: (state: UserState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    info(state: UserState): UserTO {
      return state.info;
    }
  },
  mutations: {
    setUser(state: UserState, user: UserTO): void {
      state.info = user;
    },
    setLastFetch(state: UserState): void {
      state.lastFetch = new Date().getTime();
    }
  },
  actions: {
    async getUserInfo(context: ActionContext<UserState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());

      try {
        const res = await UserRestControllerApiFactory(cfg).userinfo();

        context.commit('setUser', res.data);
        context.commit('setLastFetch');
      } catch (error: any) {
        FetchUtils.defaultCatchHandler(error, "Der Benutzer konnte nicht geladen werden. Bitte versuchen Sie es erneut.");
      }
    },
    setUser({commit}: any, payload: any) {
      commit("setUser", payload);
    }
  }
};
