package io.miragon.miranum.connect.task.impl;

import io.miragon.miranum.connect.task.api.TaskApi;
import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.exception.TaskAccessDeniedException;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class TaskApiImpl implements TaskApi {

    private final TaskOutPort taskOutPort;

    @Override
    public void completeTask(final CompleteTaskCommand command, final String user) {
        this.taskOutPort.completeTask(command);
    }

    @Override
    public void assignUserTask(AssignUserTaskCommand command, final String user, final List<String> userGroups) {
        if (!user.equals(command.getAssignee())) {
            throw new TaskAccessDeniedException("User " + user + " can not assign task to " + command.getAssignee() + ".");
        }
        this.taskOutPort.assignUserTask(command);
    }

    @Override
    public void unassignUserTask(String taskId, final String user, final List<String> userGroups) {
        this.taskOutPort.assignUserTask(new AssignUserTaskCommand(taskId, null));
    }

    @Override
    public void cancelUserTask(String taskId, final String user) {
        this.taskOutPort.cancelUserTask(taskId);
    }

}
