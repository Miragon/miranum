package io.miragon.miranum.connect.task.api;

import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.exception.TaskAccessDeniedException;
import io.miragon.miranum.connect.task.impl.TaskApiImpl;
import io.miragon.miranum.connect.task.impl.TaskOutPort;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TaskApiTest {

    private final TaskOutPort taskOutPort = mock(TaskOutPort.class);
    private final TaskApi taskApi = new TaskApiImpl(taskOutPort);

    @Test
    void testCompleteTask() {
        final CompleteTaskCommand command = CompleteTaskCommand.builder()
                .taskId("123")
                .build();

        taskApi.completeTask(command, "user");
        verify(taskOutPort).completeTask(command);
    }

    @Test
    void testAssignUserTask() {
        final AssignUserTaskCommand command = AssignUserTaskCommand.builder()
                .taskId("123")
                .assignee("user")
                .build();
        taskApi.assignUserTask(command, "user", List.of("group"));
        verify(taskOutPort).assignUserTask(command);
    }

    @Test
    void testAssignUserTask_ThrowsTaskAccessDeniedException() {
        final AssignUserTaskCommand command = AssignUserTaskCommand.builder()
                .taskId("123")
                .assignee("anotherUser")
                .build();

        assertThatThrownBy(() -> taskApi.assignUserTask(command, "user", List.of("group")))
                .isInstanceOf(TaskAccessDeniedException.class)
                .hasMessage("User user can not assign task to anotherUser.");
    }

    @Test
    void testUnassignUserTask() {
        taskApi.unassignUserTask("123", "user", List.of("group"));
        final ArgumentCaptor<AssignUserTaskCommand> commandCaptor = ArgumentCaptor.forClass(AssignUserTaskCommand.class);
        verify(taskOutPort).assignUserTask(commandCaptor.capture());
        assertThat(commandCaptor.getValue())
            .hasFieldOrPropertyWithValue("taskId", "123")
            .hasFieldOrPropertyWithValue("assignee", null);
    }

    @Test
    void testCancelUserTask() {
        taskApi.cancelUserTask("123", "user");
        verify(taskOutPort).cancelUserTask("123");
    }

}
