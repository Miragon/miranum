package io.miranum.integration.s3.application.port.in;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileExistenceException extends RuntimeException {
  public FileExistenceException(final String message) {
    super(message);
  }
}

