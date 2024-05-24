package io.miragon.miranum.platform.application.port;

import io.miragon.miranum.connect.task.api.exception.TaskAccessDeniedException;
import io.miragon.miranum.platform.application.port.in.WorkOnUserTaskInPort;
import io.miragon.miranum.platform.application.port.out.WorkOnUserTaskOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserTaskUseCase implements WorkOnUserTaskInPort {

    private final WorkOnUserTaskOutPort taskOutPort;

    @Override
    public void completeUserTask(String user, String taskId, Map<String, Object> payload) throws TaskAccessDeniedException {
        taskOutPort.completeUserTask(user, taskId, payload);
    }

    @Override
    public void assignUserTask(String user, String taskId, String assignee) throws TaskAccessDeniedException {
        taskOutPort.assignUserTask(user, taskId, assignee);
    }

    @Override
    public void unassignUserTask(String user, String taskId) throws TaskAccessDeniedException {
        taskOutPort.unassignUserTask(user, taskId);
    }
}
