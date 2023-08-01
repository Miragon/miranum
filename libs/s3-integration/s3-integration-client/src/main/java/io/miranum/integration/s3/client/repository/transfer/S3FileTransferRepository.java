package io.miranum.integration.s3.client.repository.transfer;

import io.miranum.integration.s3.client.exception.DocumentStorageClientErrorException;
import io.miranum.integration.s3.client.exception.DocumentStorageException;
import io.miranum.integration.s3.client.exception.DocumentStorageServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Slf4j
@Repository
@RequiredArgsConstructor
public class S3FileTransferRepository {

    /**
     * Gets a file from document storage using the presignedURL.
     *
     * @param presignedUrl to get the file.
     * @return the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public byte[] getFile(final String presignedUrl) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final var headers = new HttpHeaders();
            final HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
            /**
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            final ResponseEntity<byte[]> responseEntity = new RestTemplate().exchange(
                    URI.create(presignedUrl),
                    HttpMethod.GET,
                    httpEntity,
                    byte[].class
            );
            return responseEntity.getBody();
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The presigned url request failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Gets an InputStream for file from document storage using the presignedURL.
     *
     * @param presignedUrl to get the file.
     * @return the InputStream for the file.
     * @throws DocumentStorageException if the problem cannot be assigned to either the client or the S3 storage.
     */
    public InputStream getFileInputStream(final String presignedUrl) throws DocumentStorageException {
        try {
            final var urlResource = new UrlResource(presignedUrl);
            return urlResource.getInputStream();
        } catch (final IOException exception) {
            final String message = String.format("The presigned url request failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Saves the file to document storage using the presignedURL.
     *
     * @param presignedUrl to save the file.
     * @param file         to save.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void saveFile(final String presignedUrl, final byte[] file) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final var headers = new HttpHeaders();
            final HttpEntity<byte[]> fileHttpEntity = new HttpEntity<>(file, headers);
            /**
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            new RestTemplate().exchange(
                    URI.create(presignedUrl),
                    HttpMethod.PUT,
                    fileHttpEntity,
                    Void.class
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The presigned url request failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Saves the file InputStream to document storage using the presignedURL.
     *
     * @param presignedUrl to save the file.
     * @param file         to save.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void saveFileInputStream(final String presignedUrl, final InputStream file) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final var headers = new HttpHeaders();
            final HttpEntity<Resource> fileHttpEntity = new HttpEntity<>(new InputStreamResource(file), headers);
            /**
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            new RestTemplate().exchange(
                    URI.create(presignedUrl),
                    HttpMethod.PUT,
                    fileHttpEntity,
                    Void.class
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The presigned url request failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Updates the file in the document storage using the presignedURL.
     *
     * @param presignedUrl to update the file.
     * @param file         which overwrites the file in the document storage.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void updateFile(final String presignedUrl, final byte[] file) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final var headers = new HttpHeaders();
            final HttpEntity<byte[]> fileHttpEntity = new HttpEntity<>(file, headers);
            /**
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            new RestTemplate().exchange(
                    URI.create(presignedUrl),
                    HttpMethod.PUT,
                    fileHttpEntity,
                    Void.class
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The presigned url request failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Updates the file InputStream in the document storage using the presignedURL.
     *
     * @param presignedUrl to update the file.
     * @param file         which overwrites the file in the document storage.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void updateFileInputStream(final String presignedUrl, final InputStream file) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final var headers = new HttpHeaders();
            final HttpEntity<Resource> fileHttpEntity = new HttpEntity<>(new InputStreamResource(file), headers);
            /**
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            new RestTemplate().exchange(
                    URI.create(presignedUrl),
                    HttpMethod.PUT,
                    fileHttpEntity,
                    Void.class
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The presigned url request failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Deletes a file from document storage using the presignedURL.
     *
     * @param presignedUrl to delete the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage.
     */
    public void deleteFile(final String presignedUrl) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final var headers = new HttpHeaders();
            final HttpEntity<Void> fileHttpEntity = new HttpEntity<>(headers);
            /**
             * Using the RestTemplate without any authorization.
             * The presigned URL contains any authorization against the S3 storage.
             */
            new RestTemplate().exchange(
                    URI.create(presignedUrl),
                    HttpMethod.DELETE,
                    fileHttpEntity,
                    Void.class
            );
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The presigned url request failed with http status %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The presigned url request failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

}
