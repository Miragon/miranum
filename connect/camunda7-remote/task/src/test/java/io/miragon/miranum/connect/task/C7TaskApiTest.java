package io.miragon.miranum.connect.task;

import io.miragon.miranum.connect.camnda7.remote.utils.Camunda7RestValueMapper;
import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.exception.TaskOperationFailedException;
import io.miragon.miranum.connect.task.impl.TaskOutPort;
import org.camunda.community.rest.client.api.TaskApi;
import org.camunda.community.rest.client.dto.CompleteTaskDto;
import org.camunda.community.rest.client.dto.UserIdDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class C7TaskApiTest {

    private final TaskApi taskApi = mock(TaskApi.class);
    private final Camunda7RestValueMapper baseVariableMapper = new Camunda7RestValueMapper();
    private final TaskOutPort c7TaskApi = new C7TaskApi(taskApi, baseVariableMapper);

    @Test
    void completeTask() throws ApiException {
        final CompleteTaskCommand command = CompleteTaskCommand.builder()
                .taskId("123")
                .variables(Map.of("test", "test"))
                .build();
        c7TaskApi.completeTask(command);

        final ArgumentCaptor<CompleteTaskDto> completeTaskDtoCaptor = ArgumentCaptor.forClass(CompleteTaskDto.class);
        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);

        verify(taskApi).complete(taskIdCaptor.capture(), completeTaskDtoCaptor.capture());
        assertThat(taskIdCaptor.getValue()).isEqualTo("123");
        assertThat(completeTaskDtoCaptor.getValue())
                .isNotNull()
                .hasFieldOrProperty("variables");
    }

    @Test
    void completeTask_ThrowsTaskOperationFailed() throws ApiException {
        final CompleteTaskCommand command = CompleteTaskCommand.builder()
            .taskId("123")
            .variables(Map.of("test", "test"))
            .build();
        when(taskApi.complete(anyString(), any(CompleteTaskDto.class)))
            .thenThrow(new ApiException("Test"));
        assertThatThrownBy(() -> c7TaskApi.completeTask(command))
                .isInstanceOf(TaskOperationFailedException.class);
    }

    @Test
    void assignUserTask() throws ApiException {
        final AssignUserTaskCommand command = AssignUserTaskCommand.builder()
            .taskId("123")
            .assignee("user")
            .build();
        c7TaskApi.assignUserTask(command);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<UserIdDto> userIdDtoCaptor = ArgumentCaptor.forClass(UserIdDto.class);

        verify(taskApi).claim(taskIdCaptor.capture(), userIdDtoCaptor.capture());
        assertThat(taskIdCaptor.getValue()).isEqualTo("123");
        assertThat(userIdDtoCaptor.getValue())
                .isNotNull()
                .hasFieldOrPropertyWithValue("userId", "user");
    }

    @Test
    void assignUserTask_ThrowsTaskOperationFailed() throws ApiException {
        final AssignUserTaskCommand command = AssignUserTaskCommand.builder()
            .taskId("123")
            .assignee("user")
            .build();
        doThrow(new ApiException("test")).when(taskApi).claim(anyString(), any(UserIdDto.class));
        assertThatThrownBy(() -> c7TaskApi.assignUserTask(command))
                .isInstanceOf(TaskOperationFailedException.class);
    }

    @Test
    void cancelUserTask() throws ApiException {
        c7TaskApi.cancelUserTask("123");
        verify(taskApi).deleteTask("123");
    }

    @Test
    void cancelUserTask_ThrowsTaskOperationFailed() throws ApiException {
        doThrow(new ApiException("test")).when(taskApi).deleteTask(anyString());
        assertThatThrownBy(() -> c7TaskApi.cancelUserTask("123"))
                .isInstanceOf(TaskOperationFailedException.class);
    }

}
