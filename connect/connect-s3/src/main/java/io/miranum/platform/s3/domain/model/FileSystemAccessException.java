package io.miranum.platform.s3.domain.model;

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
