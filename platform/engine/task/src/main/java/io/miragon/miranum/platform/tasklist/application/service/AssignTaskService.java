package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.connect.task.api.TaskApi;
import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.application.port.in.WorkOnTaskUseCase;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssignTaskService implements WorkOnTaskUseCase {

    private final TaskApi connectTaskApi;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    public void assignUserTask(String user, String taskId, String assignee) throws TaskAccessDeniedException {
        final AssignUserTaskCommand command = AssignUserTaskCommand.builder()
            .taskId(taskId)
            .assignee(assignee)
            .build();
        connectTaskApi.assignUserTask(command, user, userAuthenticationProvider.getLoggedInUserRoles());
    }

    @Override
    public void unassignUserTask(String user, String taskId) throws TaskAccessDeniedException {
        connectTaskApi.unassignUserTask(taskId, null, userAuthenticationProvider.getLoggedInUserRoles());
    }
}
