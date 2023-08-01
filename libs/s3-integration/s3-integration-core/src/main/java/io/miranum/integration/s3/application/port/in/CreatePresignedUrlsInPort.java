package io.miranum.integration.s3.application.port.in;

import io.miranum.integration.s3.model.PresignedUrl;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface CreatePresignedUrlsInPort {
  /**
   * Create pre-signed URLs.
   *
   * @param event event containing the request.
   * @return resulting variable map.
   * @throws FileSystemAccessException                         on S3 errors.
   * @throws javax.validation.ConstraintViolationException if the request is not valid.
   */
  @NonNull
  List<PresignedUrl> createPresignedUrls(@Valid CreatePresignedUrlEvent event) throws FileSystemAccessException;
}
