import {ActionContext} from "vuex";
import {RootState} from "../index";
import {FetchUtils, InfoRestControllerApiFactory, InfoTO} from '@muenchen/digiwf-engine-api-internal';
import {ApiConfig} from "../../api/ApiConfig";

export interface InfoState {
  info: InfoTO;
  lastFetch: number | null;
}

export default {
  namespaced: true,
  state: {
    info: {},
    lastFetch: null
  } as InfoState,
  getters: {
    shouldUpdate: (state: InfoState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    info(state: InfoState): InfoTO {
      return state.info;
    },
  },
  mutations: {
    setInfo(state: InfoState, info: InfoTO): void {
      state.info = info;
    },
    setLastFetch(state: InfoState): void {
      state.lastFetch = new Date().getTime();
    },
  },
  actions: {
    async getInfo(context: ActionContext<InfoState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());

      try {

        const res = await InfoRestControllerApiFactory(cfg).getInfo();

        context.commit('setInfo', res.data);
        context.commit('setLastFetch');
      } catch (err: any) {
        FetchUtils.defaultCatchHandler(err, "Die Info konnte nicht geladen werden. Bitte versuchen Sie es erneut.");
      }

    },
  }
};
