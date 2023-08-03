package io.miranum.integration.s3.application.port.in;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Represents a technical exception
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileSystemAccessException extends Exception {
  public FileSystemAccessException(final String message) {
    super(message);
  }

  public FileSystemAccessException(final String message, final Exception exception) {
    super(message, exception);
  }

}
