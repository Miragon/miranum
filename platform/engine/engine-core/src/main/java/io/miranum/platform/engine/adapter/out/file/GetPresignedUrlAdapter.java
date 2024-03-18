package io.miranum.platform.engine.adapter.out.file;

import io.miranum.platform.engine.application.port.out.file.PresignedUrlAdapter;
import io.miranum.platform.engine.domain.file.PresignedUrlAction;
import io.miranum.platform.s3.application.port.in.FileOperationsApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Fetch presigned urls for file download
 *
 * @author ext.dl.moesle
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class GetPresignedUrlAdapter implements PresignedUrlAdapter {

    private final FileOperationsApi presignedUrlRepository;
    private final S3ProcessProperties s3Properties;


    @Override
    public String getPresignedUrl(final String pathToFile, final int expireInMinutes) throws HttpServerErrorException {
        return this.getPresignedUrl(this.s3Properties.getHttpAPI(), pathToFile, expireInMinutes);
    }

    @Override
    public String getPresignedUrl(final String documentStorageUrl, final String pathToFile, final int expireInMinutes) throws HttpServerErrorException {
        try {
            return this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes, documentStorageUrl).block();
        } catch (final Exception ex) {
            log.error("Getting presigned url for uploading file {} failed: {}", pathToFile, ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Getting presigned url for uploading file %s failed", pathToFile));
        }
    }

    @Override
    public boolean isResponsibleForAction(final PresignedUrlAction action) {
        return action.equals(PresignedUrlAction.GET);
    }
}
