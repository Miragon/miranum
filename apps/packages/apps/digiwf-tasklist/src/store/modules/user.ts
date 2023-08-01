import {ActionContext} from "vuex";
import {RootState} from "../index";
import {FetchUtils, UserDto} from '@miragon/digiwf-engine-api-internal';
import {useServices} from "../../hooks/store";

export interface UserState {
  info: UserDto;
  lastFetch: number | null;
}

export default {
  namespaced: true,
  state: {
    info: {},
    lastFetch: null
  } as UserState,
  getters: {
    shouldUpdate: (state: UserState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    info(state: UserState): UserDto {
      return state.info;
    }
  },
  mutations: {
    setUser(state: UserState, user: UserDto): void {
      state.info = user;
    },
    setLastFetch(state: UserState): void {
      state.lastFetch = new Date().getTime();
    }
  },
  actions: {
    async getUserInfo(context: ActionContext<UserState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      try {
        const auth = useServices().$auth;
        const res = await auth.getUser()

        const user: UserDto = {
          username: res!.profile.user_name,
          surname: res!.profile.family_name,
          forename: res!.profile.given_name,
        }

        console.log("USER COMES NOW");
        console.log(user);

        context.commit('setUser', user);
        context.commit('setLastFetch');
      } catch (error: any) {
        FetchUtils.defaultCatchHandler(error, "Der Benutzer konnte nicht geladen werden. Bitte versuchen Sie es erneut.");
      }
    },
    setUser({commit}: any, payload: any) {
      commit("setUser", payload);
    }
  }
};
