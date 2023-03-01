package io.miragon.miraum.fitconnect.integration.gen.api;

import io.miragon.miraum.fitconnect.integration.gen.ApiClient;

import io.miragon.miraum.fitconnect.integration.gen.model.CreateDestination;
import io.miragon.miraum.fitconnect.integration.gen.model.DestinationList;
import io.miragon.miraum.fitconnect.integration.gen.model.Error;
import io.miragon.miraum.fitconnect.integration.gen.model.JWK;
import io.miragon.miraum.fitconnect.integration.gen.model.PatchDestination;
import io.miragon.miraum.fitconnect.integration.gen.model.PrivateDestination;
import java.util.UUID;
import io.miragon.miraum.fitconnect.integration.gen.model.UpdateDestination;

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
public class InternalApi {
    private ApiClient apiClient;

    public InternalApi() {
        this(new ApiClient());
    }

    @Autowired
    public InternalApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Fügt dem Zustellpunkt einen JWK hinzu.
     * 
     * <p><b>201</b> - Schlüssel hinzugefügt oder existiert bereits
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param JWK The JWK parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addDestinationKeyRequestCreation(UUID destinationId, JWK JWK) throws WebClientResponseException {
        Object postBody = JWK;
        // verify the required parameter 'destinationId' is set
        if (destinationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'destinationId' when calling addDestinationKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'JWK' is set
        if (JWK == null) {
            throw new WebClientResponseException("Missing the required parameter 'JWK' when calling addDestinationKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("destinationId", destinationId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/v1/destinations/{destinationId}/keys", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Fügt dem Zustellpunkt einen JWK hinzu.
     * 
     * <p><b>201</b> - Schlüssel hinzugefügt oder existiert bereits
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param JWK The JWK parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> addDestinationKey(UUID destinationId, JWK JWK) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return addDestinationKeyRequestCreation(destinationId, JWK).bodyToMono(localVarReturnType);
    }

    /**
     * Fügt dem Zustellpunkt einen JWK hinzu.
     * 
     * <p><b>201</b> - Schlüssel hinzugefügt oder existiert bereits
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param JWK The JWK parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> addDestinationKeyWithHttpInfo(UUID destinationId, JWK JWK) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return addDestinationKeyRequestCreation(destinationId, JWK).toEntity(localVarReturnType);
    }

    /**
     * Fügt dem Zustellpunkt einen JWK hinzu.
     * 
     * <p><b>201</b> - Schlüssel hinzugefügt oder existiert bereits
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param JWK The JWK parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addDestinationKeyWithResponseSpec(UUID destinationId, JWK JWK) throws WebClientResponseException {
        return addDestinationKeyRequestCreation(destinationId, JWK);
    }
    /**
     * Zustellpunkt anlegen
     * Erstellung eines neuen Zustellpunktes mit entsprechender Konfiguration zum Empfang von Einreichungen. Ein detaillierte Beschreibung ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/destination) zu finden. 
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param createDestination The createDestination parameter
     * @return PrivateDestination
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createDestinationRequestCreation(CreateDestination createDestination) throws WebClientResponseException {
        Object postBody = createDestination;
        // verify the required parameter 'createDestination' is set
        if (createDestination == null) {
            throw new WebClientResponseException("Missing the required parameter 'createDestination' when calling createDestination", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json", "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<PrivateDestination> localVarReturnType = new ParameterizedTypeReference<PrivateDestination>() {};
        return apiClient.invokeAPI("/v1/destinations", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Zustellpunkt anlegen
     * Erstellung eines neuen Zustellpunktes mit entsprechender Konfiguration zum Empfang von Einreichungen. Ein detaillierte Beschreibung ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/destination) zu finden. 
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param createDestination The createDestination parameter
     * @return PrivateDestination
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PrivateDestination> createDestination(CreateDestination createDestination) throws WebClientResponseException {
        ParameterizedTypeReference<PrivateDestination> localVarReturnType = new ParameterizedTypeReference<PrivateDestination>() {};
        return createDestinationRequestCreation(createDestination).bodyToMono(localVarReturnType);
    }

    /**
     * Zustellpunkt anlegen
     * Erstellung eines neuen Zustellpunktes mit entsprechender Konfiguration zum Empfang von Einreichungen. Ein detaillierte Beschreibung ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/destination) zu finden. 
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param createDestination The createDestination parameter
     * @return ResponseEntity&lt;PrivateDestination&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PrivateDestination>> createDestinationWithHttpInfo(CreateDestination createDestination) throws WebClientResponseException {
        ParameterizedTypeReference<PrivateDestination> localVarReturnType = new ParameterizedTypeReference<PrivateDestination>() {};
        return createDestinationRequestCreation(createDestination).toEntity(localVarReturnType);
    }

    /**
     * Zustellpunkt anlegen
     * Erstellung eines neuen Zustellpunktes mit entsprechender Konfiguration zum Empfang von Einreichungen. Ein detaillierte Beschreibung ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/destination) zu finden. 
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param createDestination The createDestination parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createDestinationWithResponseSpec(CreateDestination createDestination) throws WebClientResponseException {
        return createDestinationRequestCreation(createDestination);
    }
    /**
     * Zustellpunkt löschen
     * Dieser Endpunkt ermöglicht es, einen Zustellpunkt zu löschen.
     * <p><b>204</b> - Zustellpunkt erfolgreich gelöscht.
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteDestinationRequestCreation(UUID destinationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'destinationId' is set
        if (destinationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'destinationId' when calling deleteDestination", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("destinationId", destinationId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/v1/destinations/{destinationId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Zustellpunkt löschen
     * Dieser Endpunkt ermöglicht es, einen Zustellpunkt zu löschen.
     * <p><b>204</b> - Zustellpunkt erfolgreich gelöscht.
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteDestination(UUID destinationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteDestinationRequestCreation(destinationId).bodyToMono(localVarReturnType);
    }

    /**
     * Zustellpunkt löschen
     * Dieser Endpunkt ermöglicht es, einen Zustellpunkt zu löschen.
     * <p><b>204</b> - Zustellpunkt erfolgreich gelöscht.
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteDestinationWithHttpInfo(UUID destinationId) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteDestinationRequestCreation(destinationId).toEntity(localVarReturnType);
    }

    /**
     * Zustellpunkt löschen
     * Dieser Endpunkt ermöglicht es, einen Zustellpunkt zu löschen.
     * <p><b>204</b> - Zustellpunkt erfolgreich gelöscht.
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteDestinationWithResponseSpec(UUID destinationId) throws WebClientResponseException {
        return deleteDestinationRequestCreation(destinationId);
    }
    /**
     * Eigene Zustellpunkte auflisten
     * Mit diesem Request können alle selbst angelegten Zustellpunkte sowie deren Konfigurationen abgerufen werden.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return DestinationList
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getMyDestinationsRequestCreation(Integer limit, Integer offset) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));

        final String[] localVarAccepts = { 
            "application/json", "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<DestinationList> localVarReturnType = new ParameterizedTypeReference<DestinationList>() {};
        return apiClient.invokeAPI("/v1/destinations", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Eigene Zustellpunkte auflisten
     * Mit diesem Request können alle selbst angelegten Zustellpunkte sowie deren Konfigurationen abgerufen werden.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return DestinationList
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<DestinationList> getMyDestinations(Integer limit, Integer offset) throws WebClientResponseException {
        ParameterizedTypeReference<DestinationList> localVarReturnType = new ParameterizedTypeReference<DestinationList>() {};
        return getMyDestinationsRequestCreation(limit, offset).bodyToMono(localVarReturnType);
    }

    /**
     * Eigene Zustellpunkte auflisten
     * Mit diesem Request können alle selbst angelegten Zustellpunkte sowie deren Konfigurationen abgerufen werden.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return ResponseEntity&lt;DestinationList&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<DestinationList>> getMyDestinationsWithHttpInfo(Integer limit, Integer offset) throws WebClientResponseException {
        ParameterizedTypeReference<DestinationList> localVarReturnType = new ParameterizedTypeReference<DestinationList>() {};
        return getMyDestinationsRequestCreation(limit, offset).toEntity(localVarReturnType);
    }

    /**
     * Eigene Zustellpunkte auflisten
     * Mit diesem Request können alle selbst angelegten Zustellpunkte sowie deren Konfigurationen abgerufen werden.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getMyDestinationsWithResponseSpec(Integer limit, Integer offset) throws WebClientResponseException {
        return getMyDestinationsRequestCreation(limit, offset);
    }
    /**
     * Zustellpunkt partiell aktualisieren
     * Ermöglicht es einen Zustellpunkt partiell zu aktualisieren. Ein Beispiel wäre die Änderung des Feldes &#x60;encryptionKid&#x60; bei Schlüssel-Rollover. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param patchDestination The patchDestination parameter
     * @return PrivateDestination
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec patchDestinationRequestCreation(UUID destinationId, PatchDestination patchDestination) throws WebClientResponseException {
        Object postBody = patchDestination;
        // verify the required parameter 'destinationId' is set
        if (destinationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'destinationId' when calling patchDestination", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'patchDestination' is set
        if (patchDestination == null) {
            throw new WebClientResponseException("Missing the required parameter 'patchDestination' when calling patchDestination", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("destinationId", destinationId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json", "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<PrivateDestination> localVarReturnType = new ParameterizedTypeReference<PrivateDestination>() {};
        return apiClient.invokeAPI("/v1/destinations/{destinationId}", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Zustellpunkt partiell aktualisieren
     * Ermöglicht es einen Zustellpunkt partiell zu aktualisieren. Ein Beispiel wäre die Änderung des Feldes &#x60;encryptionKid&#x60; bei Schlüssel-Rollover. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param patchDestination The patchDestination parameter
     * @return PrivateDestination
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PrivateDestination> patchDestination(UUID destinationId, PatchDestination patchDestination) throws WebClientResponseException {
        ParameterizedTypeReference<PrivateDestination> localVarReturnType = new ParameterizedTypeReference<PrivateDestination>() {};
        return patchDestinationRequestCreation(destinationId, patchDestination).bodyToMono(localVarReturnType);
    }

    /**
     * Zustellpunkt partiell aktualisieren
     * Ermöglicht es einen Zustellpunkt partiell zu aktualisieren. Ein Beispiel wäre die Änderung des Feldes &#x60;encryptionKid&#x60; bei Schlüssel-Rollover. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param patchDestination The patchDestination parameter
     * @return ResponseEntity&lt;PrivateDestination&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PrivateDestination>> patchDestinationWithHttpInfo(UUID destinationId, PatchDestination patchDestination) throws WebClientResponseException {
        ParameterizedTypeReference<PrivateDestination> localVarReturnType = new ParameterizedTypeReference<PrivateDestination>() {};
        return patchDestinationRequestCreation(destinationId, patchDestination).toEntity(localVarReturnType);
    }

    /**
     * Zustellpunkt partiell aktualisieren
     * Ermöglicht es einen Zustellpunkt partiell zu aktualisieren. Ein Beispiel wäre die Änderung des Feldes &#x60;encryptionKid&#x60; bei Schlüssel-Rollover. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param patchDestination The patchDestination parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec patchDestinationWithResponseSpec(UUID destinationId, PatchDestination patchDestination) throws WebClientResponseException {
        return patchDestinationRequestCreation(destinationId, patchDestination);
    }
    /**
     * Zustellpunkt vollständig aktualisieren
     * Ermöglicht es, einen Zustellpunkt vollständig zu aktualisieren.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param updateDestination The updateDestination parameter
     * @return PrivateDestination
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateDestinationRequestCreation(UUID destinationId, UpdateDestination updateDestination) throws WebClientResponseException {
        Object postBody = updateDestination;
        // verify the required parameter 'destinationId' is set
        if (destinationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'destinationId' when calling updateDestination", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'updateDestination' is set
        if (updateDestination == null) {
            throw new WebClientResponseException("Missing the required parameter 'updateDestination' when calling updateDestination", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("destinationId", destinationId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json", "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<PrivateDestination> localVarReturnType = new ParameterizedTypeReference<PrivateDestination>() {};
        return apiClient.invokeAPI("/v1/destinations/{destinationId}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Zustellpunkt vollständig aktualisieren
     * Ermöglicht es, einen Zustellpunkt vollständig zu aktualisieren.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param updateDestination The updateDestination parameter
     * @return PrivateDestination
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PrivateDestination> updateDestination(UUID destinationId, UpdateDestination updateDestination) throws WebClientResponseException {
        ParameterizedTypeReference<PrivateDestination> localVarReturnType = new ParameterizedTypeReference<PrivateDestination>() {};
        return updateDestinationRequestCreation(destinationId, updateDestination).bodyToMono(localVarReturnType);
    }

    /**
     * Zustellpunkt vollständig aktualisieren
     * Ermöglicht es, einen Zustellpunkt vollständig zu aktualisieren.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param updateDestination The updateDestination parameter
     * @return ResponseEntity&lt;PrivateDestination&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PrivateDestination>> updateDestinationWithHttpInfo(UUID destinationId, UpdateDestination updateDestination) throws WebClientResponseException {
        ParameterizedTypeReference<PrivateDestination> localVarReturnType = new ParameterizedTypeReference<PrivateDestination>() {};
        return updateDestinationRequestCreation(destinationId, updateDestination).toEntity(localVarReturnType);
    }

    /**
     * Zustellpunkt vollständig aktualisieren
     * Ermöglicht es, einen Zustellpunkt vollständig zu aktualisieren.
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param updateDestination The updateDestination parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateDestinationWithResponseSpec(UUID destinationId, UpdateDestination updateDestination) throws WebClientResponseException {
        return updateDestinationRequestCreation(destinationId, updateDestination);
    }
}
