package io.miragon.miranum.platform.tasklist.application.port.out.miranum;

import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.exception.TaskOperationFailedException;
import io.miragon.miranum.connect.task.impl.TaskOutPort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MiranumTaskAdapter implements TaskOutPort {

    private final TaskService taskService;

    @Override
    public void completeTask(CompleteTaskCommand command) throws TaskOperationFailedException {
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public void assignUserTask(AssignUserTaskCommand command) throws TaskOperationFailedException {
        try {
            taskService.claim(command.getTaskId(), command.getAssignee());
        } catch (final RuntimeException e) {
            throw new TaskOperationFailedException(e.getMessage());
        }
    }

    @Override
    public void cancelUserTask(String taskId) throws TaskOperationFailedException {
        try {
            taskService.setAssignee(taskId, null);
        } catch (final RuntimeException e) {
            throw new TaskOperationFailedException(e.getMessage());
        }
    }
}
