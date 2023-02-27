package io.miragon.miraum.fitconnect.integration.gen.api;

import io.miragon.miraum.fitconnect.integration.gen.ApiClient;

import io.miragon.miraum.fitconnect.integration.gen.model.Error;
import io.miragon.miraum.fitconnect.integration.gen.model.EventLog;
import io.miragon.miraum.fitconnect.integration.gen.model.JWKS;
import io.miragon.miraum.fitconnect.integration.gen.model.Submission;
import io.miragon.miraum.fitconnect.integration.gen.model.SubmissionsForPickup;
import java.util.UUID;

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
public class EinreichungsempfangApi {
    private ApiClient apiClient;

    public EinreichungsempfangApi() {
        this(new ApiClient());
    }

    @Autowired
    public EinreichungsempfangApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Event Log auslesen
     * Über diesen Endpunkt kann der [Event Log](https://docs.fitko.de/fit-connect/docs/getting-started/event-log/) eines Vorgangs abgerufen werden, um z.B. den Status des Vorgangs zu überprüfen. 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Case not found
     * @param caseId Die UUID des Vorgangs
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return EventLog
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCaseEventsRequestCreation(UUID caseId, Integer limit, Integer offset) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'caseId' is set
        if (caseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'caseId' when calling getCaseEvents", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("caseId", caseId);

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

        ParameterizedTypeReference<EventLog> localVarReturnType = new ParameterizedTypeReference<EventLog>() {};
        return apiClient.invokeAPI("/v1/cases/{caseId}/events", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Event Log auslesen
     * Über diesen Endpunkt kann der [Event Log](https://docs.fitko.de/fit-connect/docs/getting-started/event-log/) eines Vorgangs abgerufen werden, um z.B. den Status des Vorgangs zu überprüfen. 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Case not found
     * @param caseId Die UUID des Vorgangs
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return EventLog
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<EventLog> getCaseEvents(UUID caseId, Integer limit, Integer offset) throws WebClientResponseException {
        ParameterizedTypeReference<EventLog> localVarReturnType = new ParameterizedTypeReference<EventLog>() {};
        return getCaseEventsRequestCreation(caseId, limit, offset).bodyToMono(localVarReturnType);
    }

    /**
     * Event Log auslesen
     * Über diesen Endpunkt kann der [Event Log](https://docs.fitko.de/fit-connect/docs/getting-started/event-log/) eines Vorgangs abgerufen werden, um z.B. den Status des Vorgangs zu überprüfen. 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Case not found
     * @param caseId Die UUID des Vorgangs
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return ResponseEntity&lt;EventLog&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<EventLog>> getCaseEventsWithHttpInfo(UUID caseId, Integer limit, Integer offset) throws WebClientResponseException {
        ParameterizedTypeReference<EventLog> localVarReturnType = new ParameterizedTypeReference<EventLog>() {};
        return getCaseEventsRequestCreation(caseId, limit, offset).toEntity(localVarReturnType);
    }

    /**
     * Event Log auslesen
     * Über diesen Endpunkt kann der [Event Log](https://docs.fitko.de/fit-connect/docs/getting-started/event-log/) eines Vorgangs abgerufen werden, um z.B. den Status des Vorgangs zu überprüfen. 
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Case not found
     * @param caseId Die UUID des Vorgangs
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCaseEventsWithResponseSpec(UUID caseId, Integer limit, Integer offset) throws WebClientResponseException {
        return getCaseEventsRequestCreation(caseId, limit, offset);
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
    /**
     * Einreichung abrufen
     * Hierüber können die Fachdaten, Metadaten und Strukturinformation einer Einreichung vom Status submitted oder forwarded abgefragt und dann ausgewertet werden. Eine Beschreibung, wie diese Daten weiter verarbeitet werden ist [hier](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/download-submission/) zu finden. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found, not in state submitted or forwarded
     * @param submissionId Die UUID der Einreichung
     * @return Submission
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSubmissionRequestCreation(UUID submissionId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'submissionId' is set
        if (submissionId == null) {
            throw new WebClientResponseException("Missing the required parameter 'submissionId' when calling getSubmission", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("submissionId", submissionId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json", "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<Submission> localVarReturnType = new ParameterizedTypeReference<Submission>() {};
        return apiClient.invokeAPI("/v1/submissions/{submissionId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Einreichung abrufen
     * Hierüber können die Fachdaten, Metadaten und Strukturinformation einer Einreichung vom Status submitted oder forwarded abgefragt und dann ausgewertet werden. Eine Beschreibung, wie diese Daten weiter verarbeitet werden ist [hier](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/download-submission/) zu finden. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found, not in state submitted or forwarded
     * @param submissionId Die UUID der Einreichung
     * @return Submission
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Submission> getSubmission(UUID submissionId) throws WebClientResponseException {
        ParameterizedTypeReference<Submission> localVarReturnType = new ParameterizedTypeReference<Submission>() {};
        return getSubmissionRequestCreation(submissionId).bodyToMono(localVarReturnType);
    }

    /**
     * Einreichung abrufen
     * Hierüber können die Fachdaten, Metadaten und Strukturinformation einer Einreichung vom Status submitted oder forwarded abgefragt und dann ausgewertet werden. Eine Beschreibung, wie diese Daten weiter verarbeitet werden ist [hier](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/download-submission/) zu finden. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found, not in state submitted or forwarded
     * @param submissionId Die UUID der Einreichung
     * @return ResponseEntity&lt;Submission&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Submission>> getSubmissionWithHttpInfo(UUID submissionId) throws WebClientResponseException {
        ParameterizedTypeReference<Submission> localVarReturnType = new ParameterizedTypeReference<Submission>() {};
        return getSubmissionRequestCreation(submissionId).toEntity(localVarReturnType);
    }

    /**
     * Einreichung abrufen
     * Hierüber können die Fachdaten, Metadaten und Strukturinformation einer Einreichung vom Status submitted oder forwarded abgefragt und dann ausgewertet werden. Eine Beschreibung, wie diese Daten weiter verarbeitet werden ist [hier](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/download-submission/) zu finden. 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found, not in state submitted or forwarded
     * @param submissionId Die UUID der Einreichung
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSubmissionWithResponseSpec(UUID submissionId) throws WebClientResponseException {
        return getSubmissionRequestCreation(submissionId);
    }
    /**
     * Anlage abrufen
     * Ruft eine Anlage einer Einreichung vom Status submitted oder forwarded im JOSE-Format ab. Genaueres ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/download-submission/) beschrieben 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found, not in state submitted or forwarded or attachment for submission not found
     * @param submissionId Die UUID der Einreichung
     * @param attachmentId Die UUID der Anlage. Wird vom Client erstellt, um auf diese z. B. in den Fachdaten zu referenzieren.
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSubmissionAttachmentRequestCreation(UUID submissionId, UUID attachmentId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'submissionId' is set
        if (submissionId == null) {
            throw new WebClientResponseException("Missing the required parameter 'submissionId' when calling getSubmissionAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new WebClientResponseException("Missing the required parameter 'attachmentId' when calling getSubmissionAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("submissionId", submissionId);
        pathParams.put("attachmentId", attachmentId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/jose", "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI("/v1/submissions/{submissionId}/attachments/{attachmentId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Anlage abrufen
     * Ruft eine Anlage einer Einreichung vom Status submitted oder forwarded im JOSE-Format ab. Genaueres ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/download-submission/) beschrieben 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found, not in state submitted or forwarded or attachment for submission not found
     * @param submissionId Die UUID der Einreichung
     * @param attachmentId Die UUID der Anlage. Wird vom Client erstellt, um auf diese z. B. in den Fachdaten zu referenzieren.
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<String> getSubmissionAttachment(UUID submissionId, UUID attachmentId) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return getSubmissionAttachmentRequestCreation(submissionId, attachmentId).bodyToMono(localVarReturnType);
    }

    /**
     * Anlage abrufen
     * Ruft eine Anlage einer Einreichung vom Status submitted oder forwarded im JOSE-Format ab. Genaueres ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/download-submission/) beschrieben 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found, not in state submitted or forwarded or attachment for submission not found
     * @param submissionId Die UUID der Einreichung
     * @param attachmentId Die UUID der Anlage. Wird vom Client erstellt, um auf diese z. B. in den Fachdaten zu referenzieren.
     * @return ResponseEntity&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<String>> getSubmissionAttachmentWithHttpInfo(UUID submissionId, UUID attachmentId) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return getSubmissionAttachmentRequestCreation(submissionId, attachmentId).toEntity(localVarReturnType);
    }

    /**
     * Anlage abrufen
     * Ruft eine Anlage einer Einreichung vom Status submitted oder forwarded im JOSE-Format ab. Genaueres ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/receiving/download-submission/) beschrieben 
     * <p><b>200</b> - OK
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found, not in state submitted or forwarded or attachment for submission not found
     * @param submissionId Die UUID der Einreichung
     * @param attachmentId Die UUID der Anlage. Wird vom Client erstellt, um auf diese z. B. in den Fachdaten zu referenzieren.
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSubmissionAttachmentWithResponseSpec(UUID submissionId, UUID attachmentId) throws WebClientResponseException {
        return getSubmissionAttachmentRequestCreation(submissionId, attachmentId);
    }
    /**
     * Abholbereite - im Status submitted - Einreichungen auflisten
     * Mit diesem Request werden alle abholbereiten Einreichungen sowie deren Zustellpunkte aufgelistet.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param destinationId Die UUID des Zustellpunktes
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return SubmissionsForPickup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getSubmissionsForPickupRequestCreation(UUID destinationId, Integer limit, Integer offset) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "destinationId", destinationId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));

        final String[] localVarAccepts = { 
            "application/json", "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<SubmissionsForPickup> localVarReturnType = new ParameterizedTypeReference<SubmissionsForPickup>() {};
        return apiClient.invokeAPI("/v1/submissions", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Abholbereite - im Status submitted - Einreichungen auflisten
     * Mit diesem Request werden alle abholbereiten Einreichungen sowie deren Zustellpunkte aufgelistet.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param destinationId Die UUID des Zustellpunktes
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return SubmissionsForPickup
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<SubmissionsForPickup> getSubmissionsForPickup(UUID destinationId, Integer limit, Integer offset) throws WebClientResponseException {
        ParameterizedTypeReference<SubmissionsForPickup> localVarReturnType = new ParameterizedTypeReference<SubmissionsForPickup>() {};
        return getSubmissionsForPickupRequestCreation(destinationId, limit, offset).bodyToMono(localVarReturnType);
    }

    /**
     * Abholbereite - im Status submitted - Einreichungen auflisten
     * Mit diesem Request werden alle abholbereiten Einreichungen sowie deren Zustellpunkte aufgelistet.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param destinationId Die UUID des Zustellpunktes
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return ResponseEntity&lt;SubmissionsForPickup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<SubmissionsForPickup>> getSubmissionsForPickupWithHttpInfo(UUID destinationId, Integer limit, Integer offset) throws WebClientResponseException {
        ParameterizedTypeReference<SubmissionsForPickup> localVarReturnType = new ParameterizedTypeReference<SubmissionsForPickup>() {};
        return getSubmissionsForPickupRequestCreation(destinationId, limit, offset).toEntity(localVarReturnType);
    }

    /**
     * Abholbereite - im Status submitted - Einreichungen auflisten
     * Mit diesem Request werden alle abholbereiten Einreichungen sowie deren Zustellpunkte aufgelistet.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * @param destinationId Die UUID des Zustellpunktes
     * @param limit Anzahl der zurückzugebenden Ergebnisse. Maximum ist 500. Standard ist 100.
     * @param offset Startposition der Teilmenge zurückzugebender Ergebnisse aus der Gesamtergebnismenge. Standard ist 0.
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getSubmissionsForPickupWithResponseSpec(UUID destinationId, Integer limit, Integer offset) throws WebClientResponseException {
        return getSubmissionsForPickupRequestCreation(destinationId, limit, offset);
    }
    /**
     * Event senden
     * Als empfangendes System ist es notwendig die Einreichung entweder zu akzeptieren oder abzulehnen und dadurch die Zustellung abzuschließen. Hierfür muss ein entsprechendes Event im Vorgang hinterlegt werden, indem es den Zustelldienst gesendet wird. Dieses Event kann nach der Validierung der Rahmenstruktur der Einreichung versendet werden, wie [hier](https://docs.fitko.de/fit-connect/docs/receiving/process-and-acknowledge) beschrieben ist. 
     * <p><b>204</b> - Security Event Token angenommen
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Case not found
     * <p><b>409</b> - Conflict: Invalid state transition
     * <p><b>422</b> - Unprocessable Entity: Übermitteltes Security Event Token war fachlich inkorrekt. Nähere Angaben sind im Feld &#x60;issue&#x60; enthalten.
     * @param caseId Die UUID des Vorgangs
     * @param body The body parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec sendCaseEventRequestCreation(UUID caseId, String body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'caseId' is set
        if (caseId == null) {
            throw new WebClientResponseException("Missing the required parameter 'caseId' when calling sendCaseEvent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling sendCaseEvent", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("caseId", caseId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/jose"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/v1/cases/{caseId}/events", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Event senden
     * Als empfangendes System ist es notwendig die Einreichung entweder zu akzeptieren oder abzulehnen und dadurch die Zustellung abzuschließen. Hierfür muss ein entsprechendes Event im Vorgang hinterlegt werden, indem es den Zustelldienst gesendet wird. Dieses Event kann nach der Validierung der Rahmenstruktur der Einreichung versendet werden, wie [hier](https://docs.fitko.de/fit-connect/docs/receiving/process-and-acknowledge) beschrieben ist. 
     * <p><b>204</b> - Security Event Token angenommen
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Case not found
     * <p><b>409</b> - Conflict: Invalid state transition
     * <p><b>422</b> - Unprocessable Entity: Übermitteltes Security Event Token war fachlich inkorrekt. Nähere Angaben sind im Feld &#x60;issue&#x60; enthalten.
     * @param caseId Die UUID des Vorgangs
     * @param body The body parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> sendCaseEvent(UUID caseId, String body) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return sendCaseEventRequestCreation(caseId, body).bodyToMono(localVarReturnType);
    }

    /**
     * Event senden
     * Als empfangendes System ist es notwendig die Einreichung entweder zu akzeptieren oder abzulehnen und dadurch die Zustellung abzuschließen. Hierfür muss ein entsprechendes Event im Vorgang hinterlegt werden, indem es den Zustelldienst gesendet wird. Dieses Event kann nach der Validierung der Rahmenstruktur der Einreichung versendet werden, wie [hier](https://docs.fitko.de/fit-connect/docs/receiving/process-and-acknowledge) beschrieben ist. 
     * <p><b>204</b> - Security Event Token angenommen
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Case not found
     * <p><b>409</b> - Conflict: Invalid state transition
     * <p><b>422</b> - Unprocessable Entity: Übermitteltes Security Event Token war fachlich inkorrekt. Nähere Angaben sind im Feld &#x60;issue&#x60; enthalten.
     * @param caseId Die UUID des Vorgangs
     * @param body The body parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> sendCaseEventWithHttpInfo(UUID caseId, String body) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return sendCaseEventRequestCreation(caseId, body).toEntity(localVarReturnType);
    }

    /**
     * Event senden
     * Als empfangendes System ist es notwendig die Einreichung entweder zu akzeptieren oder abzulehnen und dadurch die Zustellung abzuschließen. Hierfür muss ein entsprechendes Event im Vorgang hinterlegt werden, indem es den Zustelldienst gesendet wird. Dieses Event kann nach der Validierung der Rahmenstruktur der Einreichung versendet werden, wie [hier](https://docs.fitko.de/fit-connect/docs/receiving/process-and-acknowledge) beschrieben ist. 
     * <p><b>204</b> - Security Event Token angenommen
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Case not found
     * <p><b>409</b> - Conflict: Invalid state transition
     * <p><b>422</b> - Unprocessable Entity: Übermitteltes Security Event Token war fachlich inkorrekt. Nähere Angaben sind im Feld &#x60;issue&#x60; enthalten.
     * @param caseId Die UUID des Vorgangs
     * @param body The body parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec sendCaseEventWithResponseSpec(UUID caseId, String body) throws WebClientResponseException {
        return sendCaseEventRequestCreation(caseId, body);
    }
}
