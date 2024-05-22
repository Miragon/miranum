package io.miragon.miranum.platform.tasklist.exception;

public class TaskNotFoundException extends RuntimeException {

        public TaskNotFoundException(final String message) {
            super(message);
        }
}
