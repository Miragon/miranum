package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskCommandPort;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WorkOnUserTaskServiceTest {

    private final TaskOutPort taskQueryPort = mock(TaskOutPort.class);
    private final TaskCommandPort taskCommandPort = mock(TaskCommandPort.class);

    private final WorkOnUserTaskService workOnUserTaskService = new WorkOnUserTaskService(taskQueryPort, taskCommandPort);

    @Test
    void testCompleteTask() {
        final String user = "user1";
        final String taskId = "task123";
        final Map<String, Object> payload = Map.of("key", "value");

        workOnUserTaskService.completeUserTask(user, taskId, payload);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Map<String, Object>> payloadCaptor = ArgumentCaptor.forClass(Map.class);
        verify(taskCommandPort).completeUserTask(taskIdCaptor.capture(), payloadCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
        assertThat(payloadCaptor.getValue()).isEqualTo(payload);
    }

    @Test
    void testSaveUserTask() {
        final String user = "user1";
        final String taskId = "task123";
        final Map<String, Object> payload = Map.of("key", "value");

        workOnUserTaskService.saveUserTask(user, taskId, payload);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Map<String, Object>> payloadCaptor = ArgumentCaptor.forClass(Map.class);
        verify(taskCommandPort).saveUserTask(taskIdCaptor.capture(), payloadCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
        assertThat(payloadCaptor.getValue()).isEqualTo(payload);
    }

    @Test
    void testAssignUserTask() {
        final String user = "user1";
        final String taskId = "task123";

        workOnUserTaskService.assignUserTask(user, taskId, user);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> assigneeCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskCommandPort).assignUserTask(taskIdCaptor.capture(), assigneeCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
        assertThat(assigneeCaptor.getValue()).isEqualTo(user);
    }

    @Test
    void testUnassignUserTask() {
        final String user = "user1";
        final String taskId = "task123";

        workOnUserTaskService.unassignUserTask(user, taskId);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskCommandPort).unassignUserTask(taskIdCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
    }

    @Test
    void testCancelUserTask() {
        final String user = "user1";
        final String taskId = "1";

        when(taskQueryPort.getTask(user, taskId))
                .thenReturn(Task.builder()
                        .id("1")
                        .name("Example Task")
                        .description("This is an example task")
                        .processName("Example Process")
                        .processInstanceId("1")
                        .candidateGroups("group1")
                        .form("exampleForm")
                        .build());

        workOnUserTaskService.cancelUserTask(user, taskId);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskCommandPort).cancelUserTask(taskIdCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
    }

}
