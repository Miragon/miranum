package io.miranum.platform.engine.application.port.out.file;

import io.miranum.platform.engine.domain.file.PresignedUrlAction;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Handler template for fetching presigned urls from s3 storage with for specific action
 *
 * @author ext.dl.moesle
 */
public interface PresignedUrlAdapter {

    /**
     * Obtain a presigned url from the default s3 integration service. The default s3 service is specified as property digiwf.s3.url.
     *
     * @param pathToFile
     * @param expireInMinutes specifies how long the presigned url will be valid
     * @return
     * @throws HttpServerErrorException
     */
    String getPresignedUrl(final String pathToFile, final int expireInMinutes) throws HttpServerErrorException;

    /**
     * Obtain a presigned url from the specified s3 integration service
     *
     * @param documentStorageUrl is the url for the s3 integration service
     * @param pathToFile
     * @param expireInMinutes    specifies how long the presigned url will be valid
     * @return
     * @throws HttpServerErrorException
     */
    String getPresignedUrl(final String documentStorageUrl, final String pathToFile, final int expireInMinutes) throws HttpServerErrorException;

    /**
     * Check which types of presigned urls the handler will return.
     *
     * @param action is the http Method which may be used to interact with the presigned url
     * @return
     */
    boolean isResponsibleForAction(PresignedUrlAction action);
}
