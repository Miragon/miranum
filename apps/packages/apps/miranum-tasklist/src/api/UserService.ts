import {FetchUtils} from '@muenchen/digiwf-engine-api-internal';
import {statusCodeHandler} from "./statusCodeHandling";

interface KeycloakUserResponse {
  "sub": string,
  "email_verified": boolean,
  "user_name": string,
  "name": string,
  "preferred_username": string,
  "user_roles": string[],
  "given_name": string,
  "family_name": string,
  "email": string,
  "authorities": string[]
}

export default class UserService {
  //TODO wenn aufs neue API-Gateway umgestellt wird, hier auch den Endpoint umstellen
  private static base: string | undefined = "/api/sso/userinfo/";

  /**
   * Holt die Userdaten von der URL base.
   */
  static getUser(): Promise<KeycloakUserResponse> {
    return fetch(`${this.base}`, FetchUtils.getGETConfig())
      .catch(FetchUtils.defaultCatchHandler)
      .then((response) => {
        FetchUtils.defaultResponseHandler(
          response,
          `Beim laden des Users ist ein Fehler aufgetreten.`,
            statusCodeHandler
        );
        return new Promise((resolve) => resolve(response.json()));
      });
  }
}
