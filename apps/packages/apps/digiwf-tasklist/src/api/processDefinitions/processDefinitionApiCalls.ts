import {ApiConfig} from "../ApiConfig";
import {
  FetchUtils,
  PageProcessDefinitionDto,
  ProcessDefinitionControllerApiFactory
} from "@miragon/digiwf-engine-api-internal";


export const callGetProcessDefinitionsFromEngine = (page: number, size: number, query?: string): Promise<PageProcessDefinitionDto> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());

  return ProcessDefinitionControllerApiFactory(cfg).getServiceDefinitions(page, size, query)
    .then(res => Promise.resolve(res.data))
    .catch(_ => Promise.reject("Die Vorgänge konnten nicht geladen werden. Bitte versuchen Sie es erneut."))
};
