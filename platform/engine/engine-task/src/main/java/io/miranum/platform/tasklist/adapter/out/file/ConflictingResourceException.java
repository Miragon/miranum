package io.miranum.platform.tasklist.adapter.out.file;

import lombok.AllArgsConstructor;

/**
 * Exception is thrown if a request is in conflict with an existing resource.
 */
@AllArgsConstructor
public class ConflictingResourceException extends RuntimeException {

    public ConflictingResourceException(final String message) {
        super(message);
    }
}
