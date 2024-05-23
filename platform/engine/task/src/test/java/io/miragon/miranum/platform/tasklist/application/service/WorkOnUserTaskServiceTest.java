package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.application.accesscontrol.UserTaskAccessProvider;
import io.miragon.miranum.platform.tasklist.application.accesscontrol.UserTaskAccessProviderImpl;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskCommandPort;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class WorkOnUserTaskServiceTest {

    private final TaskOutPort taskOutPort = mock(TaskOutPort.class);
    private final TaskCommandPort taskCommandPort = mock(TaskCommandPort.class);
    private final UserAuthenticationProvider authenticationProvider = mock(UserAuthenticationProvider.class);
    private final UserTaskAccessProvider userTaskAccessProvider = new UserTaskAccessProviderImpl(authenticationProvider);

    private final WorkOnUserTaskService workOnUserTaskService = new WorkOnUserTaskService(taskOutPort, taskCommandPort, userTaskAccessProvider);

    private final String user = "user1";
    private final String taskId = "task123";
    private final Task task = Task.builder()
        .id(taskId)
        .name("Example Task")
        .description("This is an example task")
        .processName("Example Process")
        .processInstanceId("1")
        .assignee(user)
        .candidateGroups("group1")
        .form("exampleForm")
        .build();

    @BeforeEach
    void setUp() {
        when(authenticationProvider.getLoggedInUserRoles()).thenReturn(List.of("group1"));
        when(taskOutPort.getTask(taskId)).thenReturn(task);
    }

    @Test
    void testCompleteTask() {
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
        workOnUserTaskService.assignUserTask(user, taskId, user);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> assigneeCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskCommandPort).assignUserTask(taskIdCaptor.capture(), assigneeCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
        assertThat(assigneeCaptor.getValue()).isEqualTo(user);
    }

    @Test
    void testAssignUserTaskFailsWithAccessDenied() {
        // different assignee and user
        assertThatThrownBy(() -> workOnUserTaskService.assignUserTask(user, taskId, "anotherUser"))
            .isInstanceOf(TaskAccessDeniedException.class)
            .hasMessage("User " + user + " can not assign task to anotherUser.");
    }

    @Test
    void testUnassignUserTask() {
        workOnUserTaskService.unassignUserTask(user, taskId);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskCommandPort).unassignUserTask(taskIdCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
    }

    @Test
    void testCancelUserTask() {
        workOnUserTaskService.cancelUserTask(user, taskId);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskCommandPort).cancelUserTask(taskIdCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
    }

    @Test
    void testTaskOperationsFailWithAccessDeniedIfUserHasNoAccessToTask() {
        final Task task = Task.builder()
                .id("123456789")
                .name("Example Task")
                .description("This is an example task")
                .processName("Example Process")
                .processInstanceId("1")
                .candidateGroups("group2")
                .form("exampleForm")
                .build();
        when(taskOutPort.getTask(task.getId())).thenReturn(task);

        final String errorMsg = "User " + user + " has no access to task " + task.getId();
        assertThatThrownBy(() -> workOnUserTaskService.completeUserTask(user, task.getId(), Map.of()))
            .isInstanceOf(TaskAccessDeniedException.class)
            .hasMessage(errorMsg);
        assertThatThrownBy(() -> workOnUserTaskService.saveUserTask(user, task.getId(), Map.of()))
            .isInstanceOf(TaskAccessDeniedException.class)
            .hasMessage(errorMsg);
        assertThatThrownBy(() -> workOnUserTaskService.assignUserTask(user, task.getId(), user))
            .isInstanceOf(TaskAccessDeniedException.class)
            .hasMessage(errorMsg);
        assertThatThrownBy(() -> workOnUserTaskService.unassignUserTask(user, task.getId()))
            .isInstanceOf(TaskAccessDeniedException.class)
            .hasMessage(errorMsg);
        assertThatThrownBy(() -> workOnUserTaskService.cancelUserTask(user, task.getId()))
            .isInstanceOf(TaskAccessDeniedException.class)
            .hasMessage(errorMsg);
    }

}
