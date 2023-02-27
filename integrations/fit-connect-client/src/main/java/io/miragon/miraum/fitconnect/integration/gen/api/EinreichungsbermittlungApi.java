package io.miragon.miraum.fitconnect.integration.gen.api;

import io.miragon.miraum.fitconnect.integration.gen.ApiClient;

import io.miragon.miraum.fitconnect.integration.gen.model.CreateSubmission;
import io.miragon.miraum.fitconnect.integration.gen.model.Destination;
import io.miragon.miraum.fitconnect.integration.gen.model.Error;
import io.miragon.miraum.fitconnect.integration.gen.model.EventLog;
import io.miragon.miraum.fitconnect.integration.gen.model.JWK;
import io.miragon.miraum.fitconnect.integration.gen.model.JWKS;
import io.miragon.miraum.fitconnect.integration.gen.model.SubmissionCreated;
import io.miragon.miraum.fitconnect.integration.gen.model.SubmissionReduced;
import io.miragon.miraum.fitconnect.integration.gen.model.SubmitSubmission;
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
public class EinreichungsbermittlungApi {
    private ApiClient apiClient;

    public EinreichungsbermittlungApi() {
        this(new ApiClient());
    }

    @Autowired
    public EinreichungsbermittlungApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Anlage hinzufügen
     * Hochladen der in &#x60;announcedAttachments&#x60; angekündigten Anlage im JOSE-Format unter der mitgeteilten UUID. Genaueres ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/sending/attachments/) beschrieben. 
     * <p><b>204</b> - Anlage erfolgreich hinzugefügt/überschrieben
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>415</b> - Unsupported Media Type (wrong content type sent)
     * <p><b>422</b> - Attachment not announced. Must upload attachments under their announced UUIDs.
     * @param submissionId Die UUID der Einreichung
     * @param attachmentId Die UUID der Anlage. Wird vom Client erstellt, um auf diese z. B. in den Fachdaten zu referenzieren.
     * @param body The body parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec addSubmissionAttachmentRequestCreation(UUID submissionId, UUID attachmentId, String body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'submissionId' is set
        if (submissionId == null) {
            throw new WebClientResponseException("Missing the required parameter 'submissionId' when calling addSubmissionAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'attachmentId' is set
        if (attachmentId == null) {
            throw new WebClientResponseException("Missing the required parameter 'attachmentId' when calling addSubmissionAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling addSubmissionAttachment", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
            "application/problem+json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/jose"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/v1/submissions/{submissionId}/attachments/{attachmentId}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Anlage hinzufügen
     * Hochladen der in &#x60;announcedAttachments&#x60; angekündigten Anlage im JOSE-Format unter der mitgeteilten UUID. Genaueres ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/sending/attachments/) beschrieben. 
     * <p><b>204</b> - Anlage erfolgreich hinzugefügt/überschrieben
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>415</b> - Unsupported Media Type (wrong content type sent)
     * <p><b>422</b> - Attachment not announced. Must upload attachments under their announced UUIDs.
     * @param submissionId Die UUID der Einreichung
     * @param attachmentId Die UUID der Anlage. Wird vom Client erstellt, um auf diese z. B. in den Fachdaten zu referenzieren.
     * @param body The body parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> addSubmissionAttachment(UUID submissionId, UUID attachmentId, String body) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return addSubmissionAttachmentRequestCreation(submissionId, attachmentId, body).bodyToMono(localVarReturnType);
    }

    /**
     * Anlage hinzufügen
     * Hochladen der in &#x60;announcedAttachments&#x60; angekündigten Anlage im JOSE-Format unter der mitgeteilten UUID. Genaueres ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/sending/attachments/) beschrieben. 
     * <p><b>204</b> - Anlage erfolgreich hinzugefügt/überschrieben
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>415</b> - Unsupported Media Type (wrong content type sent)
     * <p><b>422</b> - Attachment not announced. Must upload attachments under their announced UUIDs.
     * @param submissionId Die UUID der Einreichung
     * @param attachmentId Die UUID der Anlage. Wird vom Client erstellt, um auf diese z. B. in den Fachdaten zu referenzieren.
     * @param body The body parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> addSubmissionAttachmentWithHttpInfo(UUID submissionId, UUID attachmentId, String body) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return addSubmissionAttachmentRequestCreation(submissionId, attachmentId, body).toEntity(localVarReturnType);
    }

    /**
     * Anlage hinzufügen
     * Hochladen der in &#x60;announcedAttachments&#x60; angekündigten Anlage im JOSE-Format unter der mitgeteilten UUID. Genaueres ist in der [Dokumentation](https://docs.fitko.de/fit-connect/docs/getting-started/sending/attachments/) beschrieben. 
     * <p><b>204</b> - Anlage erfolgreich hinzugefügt/überschrieben
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>415</b> - Unsupported Media Type (wrong content type sent)
     * <p><b>422</b> - Attachment not announced. Must upload attachments under their announced UUIDs.
     * @param submissionId Die UUID der Einreichung
     * @param attachmentId Die UUID der Anlage. Wird vom Client erstellt, um auf diese z. B. in den Fachdaten zu referenzieren.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec addSubmissionAttachmentWithResponseSpec(UUID submissionId, UUID attachmentId, String body) throws WebClientResponseException {
        return addSubmissionAttachmentRequestCreation(submissionId, attachmentId, body);
    }
    /**
     * Einreichung erstellen
     * Dieser Endpunkt ist der erste, initiale Schritt zum Erstellen einer Einreichung. Danach können Anlagen hochgeladen und schließlich die Einreichung an den Zustellpunkt versendet werden, der in diesem Endpunkt angegeben wurde. 
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Destination not found
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>422</b> - Unprocessable Entity: Es konnte keine Submission angelegt werden, z.B. weil der Zustellpunkt (Destination) den falschen Status hat.
     * @param createSubmission The createSubmission parameter
     * @return SubmissionCreated
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createSubmissionRequestCreation(CreateSubmission createSubmission) throws WebClientResponseException {
        Object postBody = createSubmission;
        // verify the required parameter 'createSubmission' is set
        if (createSubmission == null) {
            throw new WebClientResponseException("Missing the required parameter 'createSubmission' when calling createSubmission", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<SubmissionCreated> localVarReturnType = new ParameterizedTypeReference<SubmissionCreated>() {};
        return apiClient.invokeAPI("/v1/submissions", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Einreichung erstellen
     * Dieser Endpunkt ist der erste, initiale Schritt zum Erstellen einer Einreichung. Danach können Anlagen hochgeladen und schließlich die Einreichung an den Zustellpunkt versendet werden, der in diesem Endpunkt angegeben wurde. 
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Destination not found
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>422</b> - Unprocessable Entity: Es konnte keine Submission angelegt werden, z.B. weil der Zustellpunkt (Destination) den falschen Status hat.
     * @param createSubmission The createSubmission parameter
     * @return SubmissionCreated
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<SubmissionCreated> createSubmission(CreateSubmission createSubmission) throws WebClientResponseException {
        ParameterizedTypeReference<SubmissionCreated> localVarReturnType = new ParameterizedTypeReference<SubmissionCreated>() {};
        return createSubmissionRequestCreation(createSubmission).bodyToMono(localVarReturnType);
    }

    /**
     * Einreichung erstellen
     * Dieser Endpunkt ist der erste, initiale Schritt zum Erstellen einer Einreichung. Danach können Anlagen hochgeladen und schließlich die Einreichung an den Zustellpunkt versendet werden, der in diesem Endpunkt angegeben wurde. 
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Destination not found
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>422</b> - Unprocessable Entity: Es konnte keine Submission angelegt werden, z.B. weil der Zustellpunkt (Destination) den falschen Status hat.
     * @param createSubmission The createSubmission parameter
     * @return ResponseEntity&lt;SubmissionCreated&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<SubmissionCreated>> createSubmissionWithHttpInfo(CreateSubmission createSubmission) throws WebClientResponseException {
        ParameterizedTypeReference<SubmissionCreated> localVarReturnType = new ParameterizedTypeReference<SubmissionCreated>() {};
        return createSubmissionRequestCreation(createSubmission).toEntity(localVarReturnType);
    }

    /**
     * Einreichung erstellen
     * Dieser Endpunkt ist der erste, initiale Schritt zum Erstellen einer Einreichung. Danach können Anlagen hochgeladen und schließlich die Einreichung an den Zustellpunkt versendet werden, der in diesem Endpunkt angegeben wurde. 
     * <p><b>201</b> - Created
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Destination not found
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>422</b> - Unprocessable Entity: Es konnte keine Submission angelegt werden, z.B. weil der Zustellpunkt (Destination) den falschen Status hat.
     * @param createSubmission The createSubmission parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createSubmissionWithResponseSpec(CreateSubmission createSubmission) throws WebClientResponseException {
        return createSubmissionRequestCreation(createSubmission);
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
     * Zustellpunkt abfragen
     * Ruft die Details eines Zustellpunktes ab.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @return Destination
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getDestinationInfoRequestCreation(UUID destinationId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'destinationId' is set
        if (destinationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'destinationId' when calling getDestinationInfo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<Destination> localVarReturnType = new ParameterizedTypeReference<Destination>() {};
        return apiClient.invokeAPI("/v1/destinations/{destinationId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Zustellpunkt abfragen
     * Ruft die Details eines Zustellpunktes ab.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @return Destination
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Destination> getDestinationInfo(UUID destinationId) throws WebClientResponseException {
        ParameterizedTypeReference<Destination> localVarReturnType = new ParameterizedTypeReference<Destination>() {};
        return getDestinationInfoRequestCreation(destinationId).bodyToMono(localVarReturnType);
    }

    /**
     * Zustellpunkt abfragen
     * Ruft die Details eines Zustellpunktes ab.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @return ResponseEntity&lt;Destination&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Destination>> getDestinationInfoWithHttpInfo(UUID destinationId) throws WebClientResponseException {
        ParameterizedTypeReference<Destination> localVarReturnType = new ParameterizedTypeReference<Destination>() {};
        return getDestinationInfoRequestCreation(destinationId).toEntity(localVarReturnType);
    }

    /**
     * Zustellpunkt abfragen
     * Ruft die Details eines Zustellpunktes ab.
     * <p><b>200</b> - OK
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getDestinationInfoWithResponseSpec(UUID destinationId) throws WebClientResponseException {
        return getDestinationInfoRequestCreation(destinationId);
    }
    /**
     * Ruft einen JWK des Zustelldienstes ab
     * Ermöglicht es, Public Keys eines Zustellpunktes abzufragen.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param keyId Die Id eines JWK
     * @return JWK
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getDestinationKeyRequestCreation(UUID destinationId, String keyId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'destinationId' is set
        if (destinationId == null) {
            throw new WebClientResponseException("Missing the required parameter 'destinationId' when calling getDestinationKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new WebClientResponseException("Missing the required parameter 'keyId' when calling getDestinationKey", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("destinationId", destinationId);
        pathParams.put("keyId", keyId);

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

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<JWK> localVarReturnType = new ParameterizedTypeReference<JWK>() {};
        return apiClient.invokeAPI("/v1/destinations/{destinationId}/keys/{keyId}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Ruft einen JWK des Zustelldienstes ab
     * Ermöglicht es, Public Keys eines Zustellpunktes abzufragen.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param keyId Die Id eines JWK
     * @return JWK
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<JWK> getDestinationKey(UUID destinationId, String keyId) throws WebClientResponseException {
        ParameterizedTypeReference<JWK> localVarReturnType = new ParameterizedTypeReference<JWK>() {};
        return getDestinationKeyRequestCreation(destinationId, keyId).bodyToMono(localVarReturnType);
    }

    /**
     * Ruft einen JWK des Zustelldienstes ab
     * Ermöglicht es, Public Keys eines Zustellpunktes abzufragen.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param keyId Die Id eines JWK
     * @return ResponseEntity&lt;JWK&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<JWK>> getDestinationKeyWithHttpInfo(UUID destinationId, String keyId) throws WebClientResponseException {
        ParameterizedTypeReference<JWK> localVarReturnType = new ParameterizedTypeReference<JWK>() {};
        return getDestinationKeyRequestCreation(destinationId, keyId).toEntity(localVarReturnType);
    }

    /**
     * Ruft einen JWK des Zustelldienstes ab
     * Ermöglicht es, Public Keys eines Zustellpunktes abzufragen.
     * <p><b>200</b> - OK
     * <p><b>404</b> - Not Found
     * @param destinationId Die UUID des Zustellpunktes
     * @param keyId Die Id eines JWK
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getDestinationKeyWithResponseSpec(UUID destinationId, String keyId) throws WebClientResponseException {
        return getDestinationKeyRequestCreation(destinationId, keyId);
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
     * Einreichung versenden
     * Über den Aufruf dieses Endpunktes wird eine vollständige Einreichung an den Zustellpunk versendet. Als sendendes System kann der Status der Einreichung überprüft werden, um herauszufinden, ob die Einreichung den Zustellpunkt erreicht hat bzw. von diesem akzeptiert wurde. 
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>415</b> - Unsupported Media Type (wrong content type sent)
     * <p><b>422</b> - Gesamtantrag unvollständig. Angekündigte Anlagen noch nicht hochgeladen.
     * @param submissionId Die UUID der Einreichung
     * @param submitSubmission The submitSubmission parameter
     * @return SubmissionReduced
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec submitSubmissionRequestCreation(UUID submissionId, SubmitSubmission submitSubmission) throws WebClientResponseException {
        Object postBody = submitSubmission;
        // verify the required parameter 'submissionId' is set
        if (submissionId == null) {
            throw new WebClientResponseException("Missing the required parameter 'submissionId' when calling submitSubmission", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'submitSubmission' is set
        if (submitSubmission == null) {
            throw new WebClientResponseException("Missing the required parameter 'submitSubmission' when calling submitSubmission", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "OAuth2" };

        ParameterizedTypeReference<SubmissionReduced> localVarReturnType = new ParameterizedTypeReference<SubmissionReduced>() {};
        return apiClient.invokeAPI("/v1/submissions/{submissionId}", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Einreichung versenden
     * Über den Aufruf dieses Endpunktes wird eine vollständige Einreichung an den Zustellpunk versendet. Als sendendes System kann der Status der Einreichung überprüft werden, um herauszufinden, ob die Einreichung den Zustellpunkt erreicht hat bzw. von diesem akzeptiert wurde. 
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>415</b> - Unsupported Media Type (wrong content type sent)
     * <p><b>422</b> - Gesamtantrag unvollständig. Angekündigte Anlagen noch nicht hochgeladen.
     * @param submissionId Die UUID der Einreichung
     * @param submitSubmission The submitSubmission parameter
     * @return SubmissionReduced
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<SubmissionReduced> submitSubmission(UUID submissionId, SubmitSubmission submitSubmission) throws WebClientResponseException {
        ParameterizedTypeReference<SubmissionReduced> localVarReturnType = new ParameterizedTypeReference<SubmissionReduced>() {};
        return submitSubmissionRequestCreation(submissionId, submitSubmission).bodyToMono(localVarReturnType);
    }

    /**
     * Einreichung versenden
     * Über den Aufruf dieses Endpunktes wird eine vollständige Einreichung an den Zustellpunk versendet. Als sendendes System kann der Status der Einreichung überprüft werden, um herauszufinden, ob die Einreichung den Zustellpunkt erreicht hat bzw. von diesem akzeptiert wurde. 
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>415</b> - Unsupported Media Type (wrong content type sent)
     * <p><b>422</b> - Gesamtantrag unvollständig. Angekündigte Anlagen noch nicht hochgeladen.
     * @param submissionId Die UUID der Einreichung
     * @param submitSubmission The submitSubmission parameter
     * @return ResponseEntity&lt;SubmissionReduced&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<SubmissionReduced>> submitSubmissionWithHttpInfo(UUID submissionId, SubmitSubmission submitSubmission) throws WebClientResponseException {
        ParameterizedTypeReference<SubmissionReduced> localVarReturnType = new ParameterizedTypeReference<SubmissionReduced>() {};
        return submitSubmissionRequestCreation(submissionId, submitSubmission).toEntity(localVarReturnType);
    }

    /**
     * Einreichung versenden
     * Über den Aufruf dieses Endpunktes wird eine vollständige Einreichung an den Zustellpunk versendet. Als sendendes System kann der Status der Einreichung überprüft werden, um herauszufinden, ob die Einreichung den Zustellpunkt erreicht hat bzw. von diesem akzeptiert wurde. 
     * <p><b>202</b> - Accepted
     * <p><b>400</b> - Bad Request
     * <p><b>401</b> - Unauthorized
     * <p><b>403</b> - Forbidden
     * <p><b>404</b> - Submission not found
     * <p><b>413</b> - Request Entity Too Large
     * <p><b>415</b> - Unsupported Media Type (wrong content type sent)
     * <p><b>422</b> - Gesamtantrag unvollständig. Angekündigte Anlagen noch nicht hochgeladen.
     * @param submissionId Die UUID der Einreichung
     * @param submitSubmission The submitSubmission parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec submitSubmissionWithResponseSpec(UUID submissionId, SubmitSubmission submitSubmission) throws WebClientResponseException {
        return submitSubmissionRequestCreation(submissionId, submitSubmission);
    }
}
