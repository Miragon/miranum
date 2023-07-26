package io.muenchendigital.digiwf.s3.integration.gen.api;

import io.muenchendigital.digiwf.s3.integration.gen.ApiClient;

import io.muenchendigital.digiwf.s3.integration.gen.model.FilesInFolderDto;

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
public class FolderApiApi {
    private ApiClient apiClient;

    public FolderApiApi() {
        this(new ApiClient());
    }

    @Autowired
    public FolderApiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * Deletes the folder specified in the parameter together with the corresponding database entry
     * <p><b>200</b> - OK
     * @param pathToFolder The pathToFolder parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteRequestCreation(String pathToFolder) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'pathToFolder' is set
        if (pathToFolder == null) {
            throw new WebClientResponseException("Missing the required parameter 'pathToFolder' when calling delete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFolder", pathToFolder));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/folder", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Deletes the folder specified in the parameter together with the corresponding database entry
     * <p><b>200</b> - OK
     * @param pathToFolder The pathToFolder parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> delete(String pathToFolder) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteRequestCreation(pathToFolder).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * Deletes the folder specified in the parameter together with the corresponding database entry
     * <p><b>200</b> - OK
     * @param pathToFolder The pathToFolder parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteWithHttpInfo(String pathToFolder) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteRequestCreation(pathToFolder).toEntity(localVarReturnType);
    }

    /**
     * 
     * Deletes the folder specified in the parameter together with the corresponding database entry
     * <p><b>200</b> - OK
     * @param pathToFolder The pathToFolder parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteWithResponseSpec(String pathToFolder) throws WebClientResponseException {
        return deleteRequestCreation(pathToFolder);
    }
    /**
     * 
     * Returns all file paths for the folder specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFolder The pathToFolder parameter
     * @return FilesInFolderDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getAllFilesInFolderRecursivelyRequestCreation(String pathToFolder) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'pathToFolder' is set
        if (pathToFolder == null) {
            throw new WebClientResponseException("Missing the required parameter 'pathToFolder' when calling getAllFilesInFolderRecursively", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFolder", pathToFolder));

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<FilesInFolderDto> localVarReturnType = new ParameterizedTypeReference<FilesInFolderDto>() {};
        return apiClient.invokeAPI("/folder", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Returns all file paths for the folder specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFolder The pathToFolder parameter
     * @return FilesInFolderDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<FilesInFolderDto> getAllFilesInFolderRecursively(String pathToFolder) throws WebClientResponseException {
        ParameterizedTypeReference<FilesInFolderDto> localVarReturnType = new ParameterizedTypeReference<FilesInFolderDto>() {};
        return getAllFilesInFolderRecursivelyRequestCreation(pathToFolder).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * Returns all file paths for the folder specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFolder The pathToFolder parameter
     * @return ResponseEntity&lt;FilesInFolderDto&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<FilesInFolderDto>> getAllFilesInFolderRecursivelyWithHttpInfo(String pathToFolder) throws WebClientResponseException {
        ParameterizedTypeReference<FilesInFolderDto> localVarReturnType = new ParameterizedTypeReference<FilesInFolderDto>() {};
        return getAllFilesInFolderRecursivelyRequestCreation(pathToFolder).toEntity(localVarReturnType);
    }

    /**
     * 
     * Returns all file paths for the folder specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFolder The pathToFolder parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getAllFilesInFolderRecursivelyWithResponseSpec(String pathToFolder) throws WebClientResponseException {
        return getAllFilesInFolderRecursivelyRequestCreation(pathToFolder);
    }
}
