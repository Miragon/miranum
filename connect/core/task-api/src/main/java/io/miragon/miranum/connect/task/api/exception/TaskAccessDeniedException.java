package io.miragon.miranum.connect.task.api.exception;

import lombok.Getter;

@Getter
public class TaskAccessDeniedException extends RuntimeException {

    public TaskAccessDeniedException(final String message) {
        super(message);
    }

}
