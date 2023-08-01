package io.miranum.integration.s3.application.port.in;

import org.springframework.transaction.annotation.Transactional;

public interface CleanUpExpiredFilesInPort {
  @Transactional
  void cleanUpExpiredFolders();
}
