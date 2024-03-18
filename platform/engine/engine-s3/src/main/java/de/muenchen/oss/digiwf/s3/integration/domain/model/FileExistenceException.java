package de.muenchen.oss.digiwf.s3.integration.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileExistenceException extends RuntimeException {
    public FileExistenceException(final String message) {
        super(message);
    }
}

