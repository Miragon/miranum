package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.auth.api.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.application.port.out.task.AssignTaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.AssignUserTask;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AssignTaskServiceTest {

    private final AssignTaskOutPort assignTaskOutPort = mock(AssignTaskOutPort.class);
    private final UserAuthenticationProvider userAuthenticationProvider = mock(UserAuthenticationProvider.class);
    private final AssignTaskService assignTaskService = new AssignTaskService(assignTaskOutPort, userAuthenticationProvider);

    final String user = "testUser";
    final String taskId = "1";

    @BeforeEach
    void setUp() {
        when(userAuthenticationProvider.getLoggedInUserRoles()).thenReturn(List.of("group1"));
    }

    @Test
    void testAssignUserTask() {
        assignTaskService.assignUserTask(user, taskId, user);

        final ArgumentCaptor<AssignUserTask> commandCaptor = ArgumentCaptor.forClass(AssignUserTask.class);
        final ArgumentCaptor<String> userCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<List> rolesCaptor = ArgumentCaptor.forClass(List.class);

        verify(assignTaskOutPort).assignUserTask(commandCaptor.capture(), userCaptor.capture(), rolesCaptor.capture());

        assertThat(commandCaptor.getValue())
                .hasFieldOrPropertyWithValue("taskId", taskId)
                .hasFieldOrPropertyWithValue("assignee", user);
        assertThat(userCaptor.getValue()).isEqualTo(user);
        assertThat(rolesCaptor.getValue()).contains("group1");
    }

    @Test
    void testAssignUserTaskThrowsTaskAccessDeniedException() {
        doThrow(new TaskAccessDeniedException("User testUser can not assign task to someOtherUser."))
                .when(assignTaskOutPort).assignUserTask(any(AssignUserTask.class), anyString(), anyList());

        assertThatThrownBy(() -> assignTaskService.assignUserTask(user, taskId, "someOtherUser"))
                .isInstanceOf(TaskAccessDeniedException.class)
                .hasMessage("User testUser can not assign task to someOtherUser.");
    }

    @Test
    void testUnassignUserTask() {
        assignTaskService.unassignUserTask(user, taskId);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> userCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<List> rolesCaptor = ArgumentCaptor.forClass(List.class);

        verify(assignTaskOutPort).unassignUserTask(taskIdCaptor.capture(), userCaptor.capture(), rolesCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
        assertThat(userCaptor.getValue()).isEqualTo(null);
        assertThat(rolesCaptor.getValue()).contains("group1");
    }

}
