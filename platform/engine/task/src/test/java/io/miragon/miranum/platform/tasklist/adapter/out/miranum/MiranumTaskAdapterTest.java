package io.miragon.miranum.platform.tasklist.adapter.out.miranum;

import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.connect.task.api.exception.TaskOperationFailedException;
import io.miragon.miranum.connect.task.impl.TaskOutPort;
import io.miragon.miranum.platform.tasklist.adapter.out.task.miranum.MiranumTaskAdapter;
import org.apache.commons.lang3.NotImplementedException;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MiranumTaskAdapterTest {

    private final TaskService taskService = mock(TaskService.class);
    private final TaskOutPort miranumTaskAdapter = new MiranumTaskAdapter(taskService);

    @Test
    void testAssignUserTask() {
        final AssignUserTaskCommand command = AssignUserTaskCommand.builder()
                .taskId("1")
                .assignee("testUser")
                .build();
        miranumTaskAdapter.assignUserTask(command);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> userCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskService).claim(taskIdCaptor.capture(), userCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo("1");
        assertThat(userCaptor.getValue()).isEqualTo("testUser");
    }

    @Test
    void testAssignUserTask_ThrowsTaskOperationFailedException() {
        doThrow(new ProcessEngineException("Error")).when(taskService).claim(anyString(), anyString());
        assertThrows(TaskOperationFailedException.class, () -> {
            final AssignUserTaskCommand command = AssignUserTaskCommand.builder()
                    .taskId("1")
                    .assignee("testUser")
                    .build();
            miranumTaskAdapter.assignUserTask(command);
        });
    }

    @Test
    void testCompleteTask() {
        assertThrows(NotImplementedException.class, () -> miranumTaskAdapter.completeTask(null));
    }

    @Test
    void testCancelUserTask() {
        assertThrows(NotImplementedException.class, () -> miranumTaskAdapter.cancelUserTask(null));
    }

}
