package io.miragon.miraum.fitconnect.integration.gen.api;

import io.miragon.miraum.fitconnect.integration.gen.ApiClient;

import io.miragon.miraum.fitconnect.integration.gen.model.Info;
import io.miragon.miraum.fitconnect.integration.gen.model.JWKS;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class TechnischApi {
    private ApiClient apiClient;

    public TechnischApi() {
        this(new ApiClient());
    }

    @Autowired
    public TechnischApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Rufe technische Serviceinformationen der Instanz ab
     * Für Debugging oder Informationszwecke angebotener Endpunkt
     * <p><b>200</b> - OK
     * @return Info
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getInfoRequestCreation() throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<Info> localVarReturnType = new ParameterizedTypeReference<Info>() {};
        return apiClient.invokeAPI("/v1/info", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rufe technische Serviceinformationen der Instanz ab
     * Für Debugging oder Informationszwecke angebotener Endpunkt
     * <p><b>200</b> - OK
     * @return Info
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Info> getInfo() throws WebClientResponseException {
        ParameterizedTypeReference<Info> localVarReturnType = new ParameterizedTypeReference<Info>() {};
        return getInfoRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * Rufe technische Serviceinformationen der Instanz ab
     * Für Debugging oder Informationszwecke angebotener Endpunkt
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;Info&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Info>> getInfoWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<Info> localVarReturnType = new ParameterizedTypeReference<Info>() {};
        return getInfoRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Rufe technische Serviceinformationen der Instanz ab
     * Für Debugging oder Informationszwecke angebotener Endpunkt
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getInfoWithResponseSpec() throws WebClientResponseException {
        return getInfoRequestCreation();
    }
    /**
     * Rufe die öffentlichen Schlüssel des Zustelldienstes ab
     * Kann z.B. zur Validierung der SETs des Zustelldienstes genutzt werden.
     * <p><b>200</b> - OK
     * @return JWKS
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getJwksJsonRequestCreation() throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<JWKS> localVarReturnType = new ParameterizedTypeReference<JWKS>() {};
        return apiClient.invokeAPI("/.well-known/jwks.json", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Rufe die öffentlichen Schlüssel des Zustelldienstes ab
     * Kann z.B. zur Validierung der SETs des Zustelldienstes genutzt werden.
     * <p><b>200</b> - OK
     * @return JWKS
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<JWKS> getJwksJson() throws WebClientResponseException {
        ParameterizedTypeReference<JWKS> localVarReturnType = new ParameterizedTypeReference<JWKS>() {};
        return getJwksJsonRequestCreation().bodyToMono(localVarReturnType);
    }

    /**
     * Rufe die öffentlichen Schlüssel des Zustelldienstes ab
     * Kann z.B. zur Validierung der SETs des Zustelldienstes genutzt werden.
     * <p><b>200</b> - OK
     * @return ResponseEntity&lt;JWKS&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<JWKS>> getJwksJsonWithHttpInfo() throws WebClientResponseException {
        ParameterizedTypeReference<JWKS> localVarReturnType = new ParameterizedTypeReference<JWKS>() {};
        return getJwksJsonRequestCreation().toEntity(localVarReturnType);
    }

    /**
     * Rufe die öffentlichen Schlüssel des Zustelldienstes ab
     * Kann z.B. zur Validierung der SETs des Zustelldienstes genutzt werden.
     * <p><b>200</b> - OK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getJwksJsonWithResponseSpec() throws WebClientResponseException {
        return getJwksJsonRequestCreation();
    }
}
