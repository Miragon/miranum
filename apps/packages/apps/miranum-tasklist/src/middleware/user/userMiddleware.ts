import {User} from "./userModels";
import {callGetUserInfoFromTaskService} from "../../api/user/userApiCalls";
import {mapUserResponse} from "./userMapper";
import {queryClient} from "../queryClient";

export const getUserInfo = (id: string) => queryClient.fetchQuery<User>({
  queryKey: ["user-info", id],
  queryFn: () => callGetUserInfoFromTaskService(id).then(data => Promise.resolve(mapUserResponse(data)))
});
