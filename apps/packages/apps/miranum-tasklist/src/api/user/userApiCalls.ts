import {ApiConfig} from "../ApiConfig";
import {FetchUtils} from "@muenchen/digiwf-engine-api-internal";
import {UserApiFactory, UserProfile} from "@muenchen/digiwf-task-api-internal";

export const callGetUserInfoFromTaskService = (id: string): Promise<UserProfile> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return UserApiFactory(cfg).resolveUser(id)
    .then((res) => Promise.resolve(res.data))
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Der Nutzer konnte nicht geladen werden. Bitte versuchen Sie es erneut.")))
};

