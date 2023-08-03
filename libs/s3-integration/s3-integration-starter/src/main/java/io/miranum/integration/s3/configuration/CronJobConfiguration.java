package io.miranum.integration.s3.configuration;

import io.miranum.integration.s3.application.port.in.CleanUpExpiredFilesInPort;
import io.miranum.integration.s3.application.port.in.CleanUpUnusedFoldersInPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(
    prefix = "io.miranum.integration.s3.cronjob.cleanup",
    name = {
        "expired-files",
        "unused-files"
    }
)
public class CronJobConfiguration {

  private final CleanUpExpiredFilesInPort cleanUpExpiredFiles;
  private final CleanUpUnusedFoldersInPort cleanUpUnusedFolders;

  @Scheduled(cron = "${io.miranum.integration.s3.cronjob.cleanup.expired-files}")
  public void cronJobDefinitionCleanUpExpiredFolders() {
    this.cleanUpExpiredFiles.cleanUpExpiredFolders();
  }

  @Scheduled(cron = "${io.miranum.integration.s3.cronjob.cleanup.unused-files}")
  public void cronJobCleanUpUnusedFolders() {
    this.cleanUpUnusedFolders.cleanUpUnusedFolders();
  }

}

