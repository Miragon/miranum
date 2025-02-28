package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.auth.api.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.application.port.in.WorkOnTaskUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.task.AssignTaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.AssignUserTask;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssignTaskService implements WorkOnTaskUseCase {

    private final AssignTaskOutPort assignTaskOutPort;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    public void assignUserTask(String user, String taskId, String assignee) throws TaskAccessDeniedException {
        assignTaskOutPort.assignUserTask(AssignUserTask.builder()
            .taskId(taskId)
            .assignee(assignee)
            .build(), user, userAuthenticationProvider.getLoggedInUserRoles());
    }

    @Override
    public void unassignUserTask(String user, String taskId) throws TaskAccessDeniedException {
        assignTaskOutPort.unassignUserTask(taskId, null, userAuthenticationProvider.getLoggedInUserRoles());
    }
}
