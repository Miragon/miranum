package io.miragon.miranum.connect.task.api;

import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;

import java.util.List;

public interface TaskApi {

    void completeTask(final CompleteTaskCommand command, final String user);
    void assignUserTask(final AssignUserTaskCommand command, final String user, final List<String> userGroups);
    void unassignUserTask(final String taskId, final String user, final List<String> userGroups);
    void cancelUserTask(final String taskId, final String user);
}
