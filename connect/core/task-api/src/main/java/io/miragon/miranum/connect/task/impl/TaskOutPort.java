package io.miragon.miranum.connect.task.impl;


import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.exception.TaskOperationFailedException;

public interface TaskOutPort {
    void completeTask(final CompleteTaskCommand command) throws TaskOperationFailedException;
    void assignUserTask(final AssignUserTaskCommand command) throws TaskOperationFailedException;
    void cancelUserTask(final String taskId) throws TaskOperationFailedException;

}
