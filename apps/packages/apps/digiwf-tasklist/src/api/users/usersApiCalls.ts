import {Configuration, FetchUtils, UserControllerApiFactory} from "@miragon/digiwf-engine-api-internal";
import {ApiConfig} from "../ApiConfig";
import {configuredAxios} from "../statusCodeHandling";

export const getUserByUsername = (username: string) => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return getFactoryFromConfig(cfg).getUserByUsername(username).then(r => Promise.resolve(r.data));
}

const getFactoryFromConfig = (cfg: Configuration) => UserControllerApiFactory(cfg, undefined, configuredAxios)

