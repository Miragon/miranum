package io.miragon.miranum.connect.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.miragon.miranum.connect.camnda7.remote.utils.Camunda7RestValueMapper;
import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.exception.TaskOperationFailedException;
import io.miragon.miranum.connect.task.impl.TaskOutPort;
import lombok.RequiredArgsConstructor;
import org.camunda.community.rest.client.api.TaskApi;
import org.camunda.community.rest.client.dto.CompleteTaskDto;
import org.camunda.community.rest.client.dto.UserIdDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class C7TaskApi implements TaskOutPort {

    private final TaskApi taskApi;
    private final Camunda7RestValueMapper baseVariableMapper;

    @Override
    public void completeTask(CompleteTaskCommand command) {
        try {
            final CompleteTaskDto completeTaskDto = new CompleteTaskDto();
            completeTaskDto.setVariables(baseVariableMapper.map(command.getVariables()));
            taskApi.complete(command.getTaskId(), completeTaskDto);
        } catch (final JsonProcessingException | ApiException e) {
            throw new TaskOperationFailedException(e.getMessage());
        }
    }

    @Override
    public void assignUserTask(AssignUserTaskCommand command) {
        try {
            final UserIdDto userIdDto = new UserIdDto();
            userIdDto.setUserId(command.getAssignee());
            taskApi.claim(command.getTaskId(), userIdDto);
        } catch (final ApiException e) {
            throw new TaskOperationFailedException(e.getMessage());
        }
    }

    @Override
    public void cancelUserTask(String taskId) {
        try {
            taskApi.deleteTask(taskId);
        } catch (final ApiException e) {
            throw new TaskOperationFailedException(e.getMessage());
        }
    }
}
