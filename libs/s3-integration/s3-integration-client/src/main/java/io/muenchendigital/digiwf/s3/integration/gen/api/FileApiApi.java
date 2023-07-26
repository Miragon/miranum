package io.muenchendigital.digiwf.s3.integration.gen.api;

import io.muenchendigital.digiwf.s3.integration.gen.ApiClient;

import io.muenchendigital.digiwf.s3.integration.gen.model.FileDataDto;
import java.time.LocalDate;
import io.muenchendigital.digiwf.s3.integration.gen.model.PresignedUrlDto;

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
public class FileApiApi {
    private ApiClient apiClient;

    public FileApiApi() {
        this(new ApiClient());
    }

    @Autowired
    public FileApiApi(ApiClient apiClient) {
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
     * Creates a presigned URL to delete the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param expiresInMinutes The expiresInMinutes parameter
     * @return PresignedUrlDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec delete1RequestCreation(String pathToFile, Integer expiresInMinutes) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'pathToFile' is set
        if (pathToFile == null) {
            throw new WebClientResponseException("Missing the required parameter 'pathToFile' when calling delete1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'expiresInMinutes' is set
        if (expiresInMinutes == null) {
            throw new WebClientResponseException("Missing the required parameter 'expiresInMinutes' when calling delete1", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFile", pathToFile));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expiresInMinutes", expiresInMinutes));

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return apiClient.invokeAPI("/file", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to delete the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param expiresInMinutes The expiresInMinutes parameter
     * @return PresignedUrlDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PresignedUrlDto> delete1(String pathToFile, Integer expiresInMinutes) throws WebClientResponseException {
        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return delete1RequestCreation(pathToFile, expiresInMinutes).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to delete the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param expiresInMinutes The expiresInMinutes parameter
     * @return ResponseEntity&lt;PresignedUrlDto&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PresignedUrlDto>> delete1WithHttpInfo(String pathToFile, Integer expiresInMinutes) throws WebClientResponseException {
        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return delete1RequestCreation(pathToFile, expiresInMinutes).toEntity(localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to delete the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param expiresInMinutes The expiresInMinutes parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec delete1WithResponseSpec(String pathToFile, Integer expiresInMinutes) throws WebClientResponseException {
        return delete1RequestCreation(pathToFile, expiresInMinutes);
    }
    /**
     * 
     * Creates a presigned URL to fetch the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param expiresInMinutes The expiresInMinutes parameter
     * @return PresignedUrlDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getRequestCreation(String pathToFile, Integer expiresInMinutes) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'pathToFile' is set
        if (pathToFile == null) {
            throw new WebClientResponseException("Missing the required parameter 'pathToFile' when calling get", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'expiresInMinutes' is set
        if (expiresInMinutes == null) {
            throw new WebClientResponseException("Missing the required parameter 'expiresInMinutes' when calling get", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFile", pathToFile));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expiresInMinutes", expiresInMinutes));

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return apiClient.invokeAPI("/file", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to fetch the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param expiresInMinutes The expiresInMinutes parameter
     * @return PresignedUrlDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PresignedUrlDto> get(String pathToFile, Integer expiresInMinutes) throws WebClientResponseException {
        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return getRequestCreation(pathToFile, expiresInMinutes).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to fetch the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param expiresInMinutes The expiresInMinutes parameter
     * @return ResponseEntity&lt;PresignedUrlDto&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PresignedUrlDto>> getWithHttpInfo(String pathToFile, Integer expiresInMinutes) throws WebClientResponseException {
        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return getRequestCreation(pathToFile, expiresInMinutes).toEntity(localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to fetch the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param expiresInMinutes The expiresInMinutes parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getWithResponseSpec(String pathToFile, Integer expiresInMinutes) throws WebClientResponseException {
        return getRequestCreation(pathToFile, expiresInMinutes);
    }
    /**
     * 
     * Creates a presigned URL to store the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param fileDataDto The fileDataDto parameter
     * @return PresignedUrlDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec saveRequestCreation(FileDataDto fileDataDto) throws WebClientResponseException {
        Object postBody = fileDataDto;
        // verify the required parameter 'fileDataDto' is set
        if (fileDataDto == null) {
            throw new WebClientResponseException("Missing the required parameter 'fileDataDto' when calling save", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return apiClient.invokeAPI("/file", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to store the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param fileDataDto The fileDataDto parameter
     * @return PresignedUrlDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PresignedUrlDto> save(FileDataDto fileDataDto) throws WebClientResponseException {
        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return saveRequestCreation(fileDataDto).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to store the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param fileDataDto The fileDataDto parameter
     * @return ResponseEntity&lt;PresignedUrlDto&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PresignedUrlDto>> saveWithHttpInfo(FileDataDto fileDataDto) throws WebClientResponseException {
        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return saveRequestCreation(fileDataDto).toEntity(localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to store the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param fileDataDto The fileDataDto parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec saveWithResponseSpec(FileDataDto fileDataDto) throws WebClientResponseException {
        return saveRequestCreation(fileDataDto);
    }
    /**
     * 
     * Creates a presigned URL to overwrite the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param fileDataDto The fileDataDto parameter
     * @return PresignedUrlDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateRequestCreation(FileDataDto fileDataDto) throws WebClientResponseException {
        Object postBody = fileDataDto;
        // verify the required parameter 'fileDataDto' is set
        if (fileDataDto == null) {
            throw new WebClientResponseException("Missing the required parameter 'fileDataDto' when calling update", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return apiClient.invokeAPI("/file", HttpMethod.PUT, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to overwrite the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param fileDataDto The fileDataDto parameter
     * @return PresignedUrlDto
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PresignedUrlDto> update(FileDataDto fileDataDto) throws WebClientResponseException {
        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return updateRequestCreation(fileDataDto).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to overwrite the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param fileDataDto The fileDataDto parameter
     * @return ResponseEntity&lt;PresignedUrlDto&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<PresignedUrlDto>> updateWithHttpInfo(FileDataDto fileDataDto) throws WebClientResponseException {
        ParameterizedTypeReference<PresignedUrlDto> localVarReturnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return updateRequestCreation(fileDataDto).toEntity(localVarReturnType);
    }

    /**
     * 
     * Creates a presigned URL to overwrite the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param fileDataDto The fileDataDto parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateWithResponseSpec(FileDataDto fileDataDto) throws WebClientResponseException {
        return updateRequestCreation(fileDataDto);
    }
    /**
     * 
     * Updates the end of life attribute in the corresponding database entry for the file specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param endOfLife The endOfLife parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec updateEndOfLifeRequestCreation(String pathToFile, LocalDate endOfLife) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'pathToFile' is set
        if (pathToFile == null) {
            throw new WebClientResponseException("Missing the required parameter 'pathToFile' when calling updateEndOfLife", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'endOfLife' is set
        if (endOfLife == null) {
            throw new WebClientResponseException("Missing the required parameter 'endOfLife' when calling updateEndOfLife", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFile", pathToFile));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endOfLife", endOfLife));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/file", HttpMethod.PATCH, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * 
     * Updates the end of life attribute in the corresponding database entry for the file specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param endOfLife The endOfLife parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> updateEndOfLife(String pathToFile, LocalDate endOfLife) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateEndOfLifeRequestCreation(pathToFile, endOfLife).bodyToMono(localVarReturnType);
    }

    /**
     * 
     * Updates the end of life attribute in the corresponding database entry for the file specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param endOfLife The endOfLife parameter
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> updateEndOfLifeWithHttpInfo(String pathToFile, LocalDate endOfLife) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return updateEndOfLifeRequestCreation(pathToFile, endOfLife).toEntity(localVarReturnType);
    }

    /**
     * 
     * Updates the end of life attribute in the corresponding database entry for the file specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFile The pathToFile parameter
     * @param endOfLife The endOfLife parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec updateEndOfLifeWithResponseSpec(String pathToFile, LocalDate endOfLife) throws WebClientResponseException {
        return updateEndOfLifeRequestCreation(pathToFile, endOfLife);
    }
}
