import {ApiConfig} from "../ApiConfig";
import {
  FetchUtils,
  PageServiceInstanceTO,
  ServiceInstanceControllerApiFactory
} from "@muenchen/digiwf-engine-api-internal";

export const callGetProcessInstances = (page: number, size: number, query?: string): Promise<PageServiceInstanceTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return ServiceInstanceControllerApiFactory(cfg)
    .getAssignedInstances(page, size, query)
    .then(response => Promise.resolve<PageServiceInstanceTO>(response.data));
};
