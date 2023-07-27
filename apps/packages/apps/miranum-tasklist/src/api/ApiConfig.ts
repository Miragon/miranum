import {Configuration} from "@muenchen/digiwf-engine-api-internal";

export class ApiConfig {

  public static base: string = import.meta.env.VITE_VUE_APP_API_URL ? import.meta.env.VITE_VUE_APP_API_URL : 'api/digitalwf-backend-service';
  public static tasklistBase: string = import.meta.env.VITE_VUE_APP_TASKLIST_API_URL ? import.meta.env.VITE_VUE_APP_TASKLIST_API_URL : 'api/digitalwf-tasklist-service/rest';

  /**
   * @deprecated
   * @param fetchConfig
   */
  static getAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.basePath = this.base;
    cfg.baseOptions = fetchConfig;
    return cfg;
  }
  static getTasklistAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.basePath = this.tasklistBase;
    cfg.baseOptions = fetchConfig;
    return cfg;
  }
}
