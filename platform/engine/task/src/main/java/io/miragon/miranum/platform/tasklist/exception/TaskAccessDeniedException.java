package io.miragon.miranum.platform.tasklist.exception;

import lombok.Getter;

@Getter
public class TaskAccessDeniedException extends RuntimeException {

    public TaskAccessDeniedException(final String message) {
        super(message);
    }

}
