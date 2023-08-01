import {useServices} from "../../hooks/store";
import {User} from "../user/userModels";
import {getUserByUsername} from "../../api/users/usersApiCalls";

export const getUserInfo = async (): Promise<String> => {
  const authService = useServices().$auth;
  const user = await authService.getUser();
  return user?.profile + "";
}

export const searchUser = (username: string): Promise<User> => {

  return getUserByUsername(username).then(r => {
    return {
      username: r.username!,
      firstName: r.forename!,
      surname: r.surname!
    }
  });

}
