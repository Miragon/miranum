import {ApiConfig} from "../ApiConfig";
import {
  FetchUtils,
  PageProcessInstanceDto,
  ProcessInstanceControllerApiFactory
} from "@miragon/digiwf-engine-api-internal";

export const callGetProcessInstances = (page: number, size: number, query?: string): Promise<PageProcessInstanceDto> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return ProcessInstanceControllerApiFactory(cfg)
    .getAssignedInstances(page, size, query)
    .then(response => Promise.resolve<PageProcessInstanceDto>(response.data));
};
