import {ActionContext} from "vuex";
import {RootState} from "../index";
import {
  FetchUtils,
  ServiceDefinitionControllerApiFactory,
  ServiceDefinitionTO
} from '@miragon/digiwf-engine-api-internal';
import {ApiConfig} from "../../api/ApiConfig";

export interface ProcessDefinitionState {
  processDefinitions: ServiceDefinitionTO[];
  lastFetch: number;
  filter: string;
}

export default {
  namespaced: true,
  state: {
    processDefinitions: new Array<ServiceDefinitionTO>(),
    lastFetch: 0,
    filter: ""
  } as ProcessDefinitionState,
  getters: {
    shouldUpdate: (state: ProcessDefinitionState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    processDefinitions(state: ProcessDefinitionState): ServiceDefinitionTO[] {
      return state.processDefinitions
        .map(obj => ({...obj, name: obj.name ?? "Unbekannt"}))
        .filter(Boolean).sort((a, b) => a.name.localeCompare(b.name));
    },
    filter(state: ProcessDefinitionState): string | null {
      return state.filter;
    }
  },
  mutations: {
    setProcessDefinitions(state: ProcessDefinitionState, processDefinitions: ServiceDefinitionTO[]): void {
      state.processDefinitions = processDefinitions;
    },
    setLastFetch(state: ProcessDefinitionState, date: number): void {
      state.lastFetch = date;
    },
    setFilter(state: ProcessDefinitionState, filter: string): void {
      state.filter = filter;
    }
  },
  actions: {
    async loadProcessDefinitions(context: ActionContext<ProcessDefinitionState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      //const processDefinitions = await ProcessService.loadProcesses();
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());

      try {
        const res = await ServiceDefinitionControllerApiFactory(cfg).getServiceDefinitions();

        context.commit('setLastFetch', new Date().getTime());
        context.commit('setProcessDefinitions', res.data);
      } catch (error: any) {
        FetchUtils.defaultCatchHandler(error, "Die Vorgänge konnten nicht geladen werden. Bitte versuchen Sie es erneut.");
      }
    }
  }
};
