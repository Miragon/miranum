import {ApiConfig} from "../ApiConfig";
import {FetchUtils} from "@miragon/digiwf-engine-api-internal";
import {UserApiFactory, UserProfile} from "@miragon/digiwf-task-api-internal";

export const callGetUserInfoFromTaskService = (id: string): Promise<UserProfile> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return UserApiFactory(cfg).resolveUser(id)
    .then((res) => Promise.resolve(res.data))
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Der Nutzer konnte nicht geladen werden. Bitte versuchen Sie es erneut.")))
};

