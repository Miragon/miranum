package io.miragon.miranum.platform.adapter.out.miranum;

import io.miragon.miranum.connect.task.api.TaskApi;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.exception.TaskAccessDeniedException;
import io.miragon.miranum.platform.application.port.out.WorkOnUserTaskOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MiranumTaskAdapter implements WorkOnUserTaskOutPort {

    private final TaskApi taskApi;

    @Override
    public void completeUserTask(String user, String taskId, Map<String, Object> payload) throws TaskAccessDeniedException {
        taskApi.completeTask(CompleteTaskCommand.builder()
            .taskId(taskId)
            .variables(payload)
            .build(), user);
    }
}
