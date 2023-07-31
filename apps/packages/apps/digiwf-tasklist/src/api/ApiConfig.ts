import {Configuration} from "@miragon/digiwf-engine-api-internal";
import {useServices} from "../hooks/store";

export class ApiConfig {

  public static base: string = import.meta.env.VITE_VUE_APP_API_URL ? import.meta.env.VITE_VUE_APP_API_URL : '';
  public static tasklistBase: string = import.meta.env.VITE_VUE_APP_TASKLIST_API_URL ? import.meta.env.VITE_VUE_APP_TASKLIST_API_URL : '/rest';


  static getAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.basePath = this.base;
    cfg.baseOptions = fetchConfig;
    const authService = useServices().$auth;
    // @ts-ignore
    cfg.accessToken = async () => {
      const user = await authService.getUser()
      return user?.access_token
    }
    return cfg;
  }

  static getTasklistAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.basePath = this.tasklistBase;
    cfg.baseOptions = fetchConfig;
    const authService = useServices().$auth;
    // @ts-ignore
    cfg.accessToken = async () => {
      const user = await authService.getUser()
      return user?.access_token
    }
    return cfg;
  }
}
