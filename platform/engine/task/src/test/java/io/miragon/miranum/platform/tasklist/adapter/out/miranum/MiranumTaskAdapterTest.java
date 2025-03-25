package io.miragon.miranum.platform.tasklist.adapter.out.miranum;

import io.miragon.miranum.platform.tasklist.adapter.out.task.miranum.MiranumTaskAdapter;
import io.miragon.miranum.platform.tasklist.application.port.out.task.AssignTaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.AssignUserTask;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MiranumTaskAdapterTest {

    private final TaskService taskService = mock(TaskService.class);
    private final AssignTaskOutPort miranumTaskAdapter = new MiranumTaskAdapter(taskService);

    @Test
    void testAssignUserTask() {
        final AssignUserTask assignUserTask = AssignUserTask.builder()
                .taskId("1")
                .assignee("testUser")
                .build();
        miranumTaskAdapter.assignUserTask(assignUserTask, "testUser", List.of());

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> userCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskService).claim(taskIdCaptor.capture(), userCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo("1");
        assertThat(userCaptor.getValue()).isEqualTo("testUser");
    }

    @Test
    void testAssignUserTask_ThrowsTaskOperationFailedException() {
        doThrow(new ProcessEngineException("Error")).when(taskService).claim(anyString(), anyString());
        assertThrows(RuntimeException.class, () -> {
            final AssignUserTask assignUserTask = AssignUserTask.builder()
                    .taskId("1")
                    .assignee("testUser")
                    .build();
            miranumTaskAdapter.assignUserTask(assignUserTask, "testUser", List.of());
        });
    }
}
