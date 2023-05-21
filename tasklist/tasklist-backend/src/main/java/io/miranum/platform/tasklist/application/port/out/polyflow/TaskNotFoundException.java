package io.miranum.platform.tasklist.application.port.out.polyflow;

/**
 * Signals that the task with given id is not available.
 */
public class TaskNotFoundException extends RuntimeException {
    /**
     * Creates new exception with corresponding error message.
     *
     * @param taskId task id to report the error for.
     */
    public TaskNotFoundException(String taskId) {
        super("Task with id '" + taskId + "' could not be found.");
    }
}
