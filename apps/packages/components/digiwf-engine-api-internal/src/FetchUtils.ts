import {Configuration} from '@/api';
import {ApiError, Levels} from "@/error";

export class FetchUtils {


  /**
   * Liefert eine default GET-Config für fetch
   */
  static getGETConfig(): RequestInit {
    return {
      headers: this.getHeaders(),
      mode: 'cors',
      credentials: 'same-origin',
      redirect: 'manual'
    };
  }

  /**
   * Liefert eine default DELETE-Config für fetch
   */
  // eslint-disable-next-line
  static getDELETEConfig(): RequestInit {
    return {
      method: 'DELETE',
      headers: this.getHeaders(),
      mode: 'cors',
      credentials: 'same-origin',
      redirect: "manual"
    };
  }

  static getAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.baseOptions = fetchConfig;
    return cfg;
  }

  /**
   * Liefert eine default POST-Config für fetch
   * @param body Optional zu übertragender Body
   */
  // eslint-disable-next-line
  static getPOSTConfig(body: any): RequestInit {
    return {
      method: 'POST',
      body: body ? JSON.stringify(body) : undefined,
      headers: FetchUtils.getHeaders(),
      mode: 'cors',
      credentials: 'same-origin',
      redirect: "manual"
    };
  }

  /**
   * Liefert eine default PUT-Config für fetch
   * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
   * als "If-Match"-Header mitgesetzt.
   * @param body Optional zu übertragender Body
   */
  // eslint-disable-next-line
  static getPUTConfig(body: any): RequestInit {
    const headers = FetchUtils.getHeaders();
    if (body.version) {
      headers.append("If-Match", body.version);
    }
    return {
      method: 'PUT',
      body: body ? JSON.stringify(body) : undefined,
      headers,
      mode: 'cors',
      credentials: 'same-origin',
      redirect: "manual"
    };

  }

  /**
   * Deckt das Default-Handling einer Response ab. Dazu zählt:
   *
   * - Fehler bei fehlenden Berechtigungen --> HTTP 403
   * - Reload der App bei Session-Timeout --> HTTP 3xx
   * - Default-Fehler bei allen HTTP-Codes !2xx
   *
   * @param response Die response aus fetch-Befehl die geprüft werden soll.
   * @param errorMessage Die Fehlermeldung, welche bei einem HTTP-Code != 2xx angezeigt werden soll.
   * @param statusCodeSpecificHandler
   */
  static defaultResponseHandler(response: Response, errorMessage = "Es ist ein unbekannter Fehler aufgetreten.", statusCodeSpecificHandler?: HttpSpecificCallbackFunction<Response>): void {
    if (!response.ok) {
      if(statusCodeSpecificHandler && statusCodeSpecificHandler[response.status]) {
        statusCodeSpecificHandler[response.status]!!(response)
      }
      if (response.status === 403) {
        throw new ApiError({
          level: Levels.ERROR,
          message: `Sie haben nicht die nötigen Rechte um diese Aktion durchzuführen.`
        });
      } else if (response.type === "opaqueredirect") {
        location.reload();
      }
      throw new ApiError({
        level: Levels.WARNING,
        message: errorMessage
      });
    }
  }


  /**
   * Default Catch-Handler für alle Anfragen des Service.
   * Schmeißt derzeit nur einen ApiError
   * @param error die Fehlermeldung aus fetch-Befehl
   */
  static defaultCatchHandler(error: Error, errorMessage = "Es ist ein unbekannter Fehler aufgetreten."): PromiseLike<never> {
    if (error instanceof ApiError) {
      throw error;
    }
    throw new ApiError({
      level: Levels.WARNING,
      message: errorMessage
    });
  }

  /**
   * Liefert eine default PATCH-Config für fetch
   * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
   * als "If-Match"-Header mitgesetzt.
   * @param body Optional zu übertragender Body
   */
  // eslint-disable-next-line
  static getPATCHConfig(body: any): RequestInit {
    const headers = FetchUtils.getHeaders();
    if (body.version !== undefined) {
      headers.append("If-Match", body.version);
    }
    return {
      method: 'PATCH',
      body: body ? JSON.stringify(body) : undefined,
      headers,
      mode: 'cors',
      credentials: 'same-origin',
      redirect: "manual"
    };
  }

  /**
   *  Baut den Header fuer den Request auf
   * @returns {Headers}
   */
  static getHeaders(): Headers {
    const headers = new Headers({
      'Content-Type': 'application/json'
    });
    const csrfCookie = this._getXSRFToken();
    if (csrfCookie !== '') {
      headers.append('X-XSRF-TOKEN', csrfCookie);
    }
    return headers;
  }

  /**
   * Liefert den XSRF-TOKEN zurück.
   * @returns {string|string}
   */
  static _getXSRFToken(): string {
    const help = document.cookie.match('(^|;)\\s*' + 'XSRF-TOKEN' + '\\s*=\\s*([^;]+)');
    return (help ? help.pop() : '') as string;
  }

}

export interface HttpSpecificCallbackFunction<T> {
  readonly [key: number]: ((response: T) => void) | undefined;
}
