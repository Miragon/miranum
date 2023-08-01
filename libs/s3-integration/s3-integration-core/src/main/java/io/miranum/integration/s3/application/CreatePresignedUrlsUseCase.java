package io.miranum.integration.s3.application;

import io.minio.http.Method;
import io.miranum.integration.s3.application.port.in.CreatePresignedUrlsInPort;
import io.miranum.integration.s3.application.port.in.FileSystemAccessException;
import io.miranum.integration.s3.model.PresignedUrl;
import io.miranum.integration.s3.application.port.in.CreatePresignedUrlEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Validated
@Transactional
public class CreatePresignedUrlsUseCase implements CreatePresignedUrlsInPort {

  private final FileOperationsUseCase fileHandlingService;
  private final int presignedUrlExpiresInMinutes;

  @Override
  @NonNull
  public List<PresignedUrl> createPresignedUrls(@Valid CreatePresignedUrlEvent event) throws FileSystemAccessException {
    // No end of life is set for files to be saved
    return this.fileHandlingService.getPresignedUrls(
        List.of(event.getPath().split(";")),
        Method.valueOf(event.getAction()),
        this.presignedUrlExpiresInMinutes // 7 days is max expiration
    );
  }

}
