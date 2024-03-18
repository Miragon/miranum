package io.miranum.platform.tasklist.adapter.out.file;

import io.miranum.integration.s3.client.repository.presignedurl.PresignedUrlRepository;
import io.miranum.platform.tasklist.application.port.out.file.PresignedUrlPort;
import io.miranum.platform.tasklist.domain.PresignedUrlAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

@Component
@Slf4j
@RequiredArgsConstructor
public class PresignedUrlAdapter implements PresignedUrlPort {

    private final PresignedUrlRepository presignedUrlRepository;
    private final S3Properties s3Properties;


    @Override
    public String getPresignedUrl(final String pathToFile, final int expireInMinutes, final PresignedUrlAction action) throws HttpServerErrorException {
        return this.getPresignedUrl(this.s3Properties.getHttpAPI(), pathToFile, expireInMinutes, action);
    }

    @Override
    public String getPresignedUrl(final String documentStorageUrl, final String pathToFile, final int expireInMinutes, final PresignedUrlAction action) throws HttpServerErrorException {
        String actionString = "";
        try {
            if (action == PresignedUrlAction.GET) {
                actionString = "downloading";
                return this.presignedUrlRepository.getPresignedUrlGetFile(pathToFile, expireInMinutes, documentStorageUrl).block();
            } else if (action == PresignedUrlAction.POST) {
                actionString = "uploading";
                return this.presignedUrlRepository.getPresignedUrlSaveFile(pathToFile, expireInMinutes, null, documentStorageUrl);
            } else if (action == PresignedUrlAction.DELETE) {
                actionString = "deleting";
                return this.presignedUrlRepository.getPresignedUrlDeleteFile(pathToFile, expireInMinutes, documentStorageUrl);
            } else {
                log.warn("No handler specified for action {}", action);
                throw new RuntimeException(String.format("No handler specified for action %s", action));
            }
        } catch (final Exception ex) {
            log.error("Getting presigned url for %s file {} failed: {}", actionString, pathToFile, ex);
            if (ex.getMessage().contains("No handler specified for action")) {
                throw new RuntimeException(ex.getMessage());
            }
            if (action == PresignedUrlAction.POST && ex.getMessage().contains(HttpStatus.CONFLICT.toString())) {
                throw new ConflictingResourceException(String.format("Getting presigned url for " + actionString + " file " + pathToFile + " failed"));
            }
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Getting presigned url for " + actionString + " file " + pathToFile + " failed"));
        }
    }

}
