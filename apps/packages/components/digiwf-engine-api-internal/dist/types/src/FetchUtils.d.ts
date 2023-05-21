import { Configuration } from '@/api';
export declare class FetchUtils {
    /**
     * Liefert eine default GET-Config für fetch
     */
    static getGETConfig(): RequestInit;
    /**
     * Liefert eine default DELETE-Config für fetch
     */
    static getDELETEConfig(): RequestInit;
    static getAxiosConfig(fetchConfig: RequestInit): Configuration;
    /**
     * Liefert eine default POST-Config für fetch
     * @param body Optional zu übertragender Body
     */
    static getPOSTConfig(body: any): RequestInit;
    /**
     * Liefert eine default PUT-Config für fetch
     * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
     * als "If-Match"-Header mitgesetzt.
     * @param body Optional zu übertragender Body
     */
    static getPUTConfig(body: any): RequestInit;
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
    static defaultResponseHandler(response: Response, errorMessage?: string, statusCodeSpecificHandler?: HttpSpecificCallbackFunction<Response>): void;
    /**
     * Default Catch-Handler für alle Anfragen des Service.
     * Schmeißt derzeit nur einen ApiError
     * @param error die Fehlermeldung aus fetch-Befehl
     */
    static defaultCatchHandler(error: Error, errorMessage?: string): PromiseLike<never>;
    /**
     * Liefert eine default PATCH-Config für fetch
     * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
     * als "If-Match"-Header mitgesetzt.
     * @param body Optional zu übertragender Body
     */
    static getPATCHConfig(body: any): RequestInit;
    /**
     *  Baut den Header fuer den Request auf
     * @returns {Headers}
     */
    static getHeaders(): Headers;
    /**
     * Liefert den XSRF-TOKEN zurück.
     * @returns {string|string}
     */
    static _getXSRFToken(): string;
}
export interface HttpSpecificCallbackFunction<T> {
    readonly [key: number]: ((response: T) => void) | undefined;
}
