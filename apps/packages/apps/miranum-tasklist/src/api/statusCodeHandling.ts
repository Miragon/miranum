import Vuexstore from "../store";
import globalAxios, {AxiosResponse} from "axios";
import {HttpSpecificCallbackFunction} from "@muenchen/digiwf-engine-api-internal/src";

export const statusCodeHandler: HttpSpecificCallbackFunction<AxiosResponse | Response> = {
  302: () => Vuexstore.commit("user/setUser", {})
}

export const configuredAxios = globalAxios.create();

export const initStatusCodeHandling = () => {
  configuredAxios.interceptors.response.use(
    (response) => {
      if (response.status) {
        const handler = statusCodeHandler[response.status];
        if (handler) {
          handler(response)
        }
      }
      return response;
    },
    (error) => {
      const configUrl = error.config.url;
      const responseURLPath= new URL(error.request.responseURL).pathname;

      // if request is a cors exception or was automatically forwarded
      if(error.code === "ERR_NETWORK" || !configUrl.startsWith(responseURLPath)) {
        console.log("Request was redirect to other endpoint")
        statusCodeHandler[302] && statusCodeHandler[302](error.response);
      }
      return error
    }
  );
}
