package io.miranum.integration.s3.client.repository;

import io.miranum.integration.s3.client.exception.DocumentStorageClientErrorException;
import io.miranum.integration.s3.client.exception.DocumentStorageException;
import io.miranum.integration.s3.client.exception.DocumentStorageServerErrorException;
import io.miranum.integration.s3.client.exception.PropertyNotSetException;
import io.miranum.integration.s3.client.repository.transfer.S3FileTransferRepository;
import io.miranum.integration.s3.client.service.ApiClientFactory;
import io.miranum.integration.s3.client.repository.presignedurl.PresignedUrlRepository;
import io.miranum.integration.s3.gen.api.FileApiApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.time.LocalDate;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DocumentStorageFileRepository {

    private final PresignedUrlRepository presignedUrlRepository;

    private final S3FileTransferRepository s3FileTransferRepository;

    private final ApiClientFactory apiClientFactory;

    /**
     * Gets the file specified in the parameter from the document storage.
     *
     * @param pathToFile      defines the path to the file.
     * @param expireInMinutes the expiration time of the presignedURL in minutes.
     * @return the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public byte[] getFile(final String pathToFile, final int expireInMinutes) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        return this.getFile(
                pathToFile,
                expireInMinutes,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Gets the file specified in the parameter from the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @return the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public byte[] getFile(final String pathToFile, final int expireInMinutes, final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final Mono<String> presignedUrl = this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes, documentStorageUrl);
        return this.s3FileTransferRepository.getFile(presignedUrl.block());
    }

    /**
     * Gets an InputStream for the file specified in the parameter from the document storage.
     *
     * @param pathToFile      defines the path to the file.
     * @param expireInMinutes the expiration time of the presignedURL in minutes.
     * @return the InputStream for the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public InputStream getFileInputStream(final String pathToFile, final int expireInMinutes) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        return this.getFileInputStream(
                pathToFile,
                expireInMinutes,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Gets an InputStream for the file specified in the parameter from the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @return the InputStream for the file.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public InputStream getFileInputStream(final String pathToFile, final int expireInMinutes, final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final Mono<String> presignedUrl = this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes, documentStorageUrl);
        return this.s3FileTransferRepository.getFileInputStream(presignedUrl.block());
    }

    /**
     * Saves the file specified in the parameter to the document storage.
     *
     * @param pathToFile      defines the path to the file.
     * @param file            to save.
     * @param expireInMinutes the expiration time of the presignedURL in minutes.
     * @param endOfLifeFolder the end of life of the folder defined in refId.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public void saveFile(final String pathToFile, final byte[] file, final int expireInMinutes, final LocalDate endOfLifeFolder) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        this.saveFile(
                pathToFile,
                file,
                expireInMinutes,
                endOfLifeFolder,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Saves the file specified in the parameter to the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param file               to save.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param endOfLifeFolder    the end of life of the folder defined in refId.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void saveFile(final String pathToFile, final byte[] file, final int expireInMinutes, final LocalDate endOfLifeFolder, final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLifeFolder, documentStorageUrl);
        this.s3FileTransferRepository.saveFile(presignedUrl, file);
    }

    /**
     * Saves the file specified in the parameter to the document storage.
     *
     * @param pathToFile      defines the path to the file.
     * @param file            to save.
     * @param expireInMinutes the expiration time of the presignedURL in minutes.
     * @param endOfLifeFolder the end of life of the folder defined in refId.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public void saveFileInputStream(final String pathToFile, final InputStream file, final int expireInMinutes, final LocalDate endOfLifeFolder) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        this.saveFileInputStream(
                pathToFile,
                file,
                expireInMinutes,
                endOfLifeFolder,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Saves the file specified in the parameter to the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param file               to save.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param endOfLifeFolder    the end of life of the folder defined in refId.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void saveFileInputStream(final String pathToFile, final InputStream file, final int expireInMinutes, final LocalDate endOfLifeFolder, final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, endOfLifeFolder, documentStorageUrl);
        this.s3FileTransferRepository.saveFileInputStream(presignedUrl, file);
    }

    /**
     * Updates the file specified in the parameter to the document storage.
     *
     * @param pathToFile      defines the path to the file.
     * @param file            which overwrites the file in the document storage.
     * @param expireInMinutes the expiration time of the presignedURL in minutes.
     * @param endOfLifeFolder the end of life of the folder defined in refId.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public void updateFile(final String pathToFile, final byte[] file, final int expireInMinutes, final LocalDate endOfLifeFolder) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        this.updateFile(
                pathToFile,
                file,
                expireInMinutes,
                endOfLifeFolder,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Updates the file specified in the parameter to the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param file               which overwrites the file in the document storage.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param endOfLifeFolder    the end of life of the folder defined in refId.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void updateFile(final String pathToFile, final byte[] file, final int expireInMinutes, final LocalDate endOfLifeFolder, final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLifeFolder, documentStorageUrl);
        this.s3FileTransferRepository.updateFile(presignedUrl, file);
    }

    /**
     * Updates the file specified in the parameter withinq the document storage.
     *
     * @param pathToFile      defines the path to the file.
     * @param file            which overwrites the file in the document storage.
     * @param expireInMinutes the expiration time of the presignedURL in minutes.
     * @param endOfLifeFolder the end of life of the folder defined in refId.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public void updateFileInputStream(final String pathToFile, final InputStream file, final int expireInMinutes, final LocalDate endOfLifeFolder) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        this.updateFileInputStream(
                pathToFile,
                file,
                expireInMinutes,
                endOfLifeFolder,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Updates the file specified in the parameter withinq the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param file               which overwrites the file in the document storage.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param endOfLifeFolder    the end of life of the folder defined in refId.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void updateFileInputStream(final String pathToFile, final InputStream file, final int expireInMinutes, final LocalDate endOfLifeFolder, final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlUpdateFile(pathToFile, expireInMinutes, endOfLifeFolder, documentStorageUrl);
        this.s3FileTransferRepository.updateFileInputStream(presignedUrl, file);
    }

    /**
     * Updates the end of life for the file given in the parameter within the document storage.
     *
     * @param pathToFile      defines the path to the file.
     * @param endOfLifeFolder the end of life of the folder defined in refId.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public void updateEndOfLife(final String pathToFile, final LocalDate endOfLifeFolder) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException, PropertyNotSetException {
        this.updateEndOfLife(
                pathToFile,
                endOfLifeFolder,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Updates the end of life for the file given in the parameter within the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param endOfLifeFolder    the end of life of the folder defined in refId.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void updateEndOfLife(final String pathToFile, final LocalDate endOfLifeFolder, final String documentStorageUrl) throws DocumentStorageClientErrorException, DocumentStorageServerErrorException, DocumentStorageException {
        try {
            final FileApiApi fileApi = this.apiClientFactory.getFileApiForDocumentStorageUrl(documentStorageUrl);
            fileApi.updateEndOfLife(pathToFile, endOfLifeFolder);
        } catch (final HttpClientErrorException exception) {
            final String message = String.format("The request to update the end of life for a file  failed %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageClientErrorException(message, exception);
        } catch (final HttpServerErrorException exception) {
            final String message = String.format("The request to update the end of life for a file  failed %s.", exception.getStatusCode());
            log.error(message);
            throw new DocumentStorageServerErrorException(message, exception);
        } catch (final RestClientException exception) {
            final String message = String.format("The request to update the end of life for a file  failed.");
            log.error(message);
            throw new DocumentStorageException(message, exception);
        }
    }

    /**
     * Deletes the file specified in the parameter from the document storage.
     *
     * @param pathToFile      defines the path to the file.
     * @param expireInMinutes the expiration time of the presignedURL in minutes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     * @throws PropertyNotSetException             if the property "io.muenchendigital.digiwf.s3.client.defaultDocumentStorageUrl" is not set.
     */
    public void deleteFile(final String pathToFile, final int expireInMinutes) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException, PropertyNotSetException {
        this.deleteFile(
                pathToFile,
                expireInMinutes,
                this.apiClientFactory.getDefaultDocumentStorageUrl()
        );
    }

    /**
     * Deletes the file specified in the parameter from the document storage.
     *
     * @param pathToFile         defines the path to the file.
     * @param expireInMinutes    the expiration time of the presignedURL in minutes.
     * @param documentStorageUrl to define to which document storage the request goes.
     * @throws DocumentStorageClientErrorException if the problem is with the client.
     * @throws DocumentStorageServerErrorException if the problem is with the S3 storage or document storage.
     * @throws DocumentStorageException            if the problem cannot be assigned to either the client or the S3 storage or the document storage.
     */
    public void deleteFile(final String pathToFile, final int expireInMinutes, final String documentStorageUrl) throws DocumentStorageException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String presignedUrl = this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes, documentStorageUrl);
        this.s3FileTransferRepository.deleteFile(presignedUrl);
    }

}
