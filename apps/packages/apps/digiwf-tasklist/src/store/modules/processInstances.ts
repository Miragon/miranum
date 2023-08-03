import {ActionContext} from "vuex";
import {RootState} from "../index";
import {FetchUtils, ProcessInstanceControllerApiFactory, ProcessInstanceDto} from '@miragon/digiwf-engine-api-internal';
import {ApiConfig} from "../../api/ApiConfig";

export interface ProcessInstancesState {
  processInstances: ProcessInstanceDto[];
  lastFetch: number;
  filter: string;
}

export default {
  namespaced: true,
  state: {
    processInstances: new Array<ProcessInstanceDto>(),
    lastFetch: 0,
    filter: ""
  } as ProcessInstancesState,
  getters: {
    shouldUpdate: (state: ProcessInstancesState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    processInstances(state: ProcessInstancesState): ProcessInstanceDto[] {
      return state.processInstances.filter(Boolean).sort((a, b) => b.startTime!.localeCompare(a.startTime!));
    },
    filter(state: ProcessInstancesState): string | null {
      return state.filter;
    }
  },
  mutations: {
    setProcessInstances(state: ProcessInstancesState, processInstances: ProcessInstanceDto[]): void {
      state.processInstances = processInstances;
    },
    setLastFetch(state: ProcessInstancesState, date: number): void {
      state.lastFetch = date;
    },
    setFilter(state: ProcessInstancesState, filter: string): void {
      state.filter = filter;
    }
  },
  actions: {
    async getProcessInstances(context: ActionContext<ProcessInstancesState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      //const processInstances = await ProcessService.loadMyInstances();
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());

      try {
        const res = await ProcessInstanceControllerApiFactory(cfg).getAssignedInstances();

        context.commit('setLastFetch', new Date().getTime());
        context.commit('setProcessInstances', res.data);
      } catch (error: any) {
        FetchUtils.defaultCatchHandler(error, "Die Vorgänge konnten nicht geladen werden. Bitte versuchen Sie es erneut.");
      }
    }
  }
};
