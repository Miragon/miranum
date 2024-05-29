package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.connect.task.api.TaskApi;
import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AssignTaskServiceTest {

    private final TaskApi connectTaskApi = mock(TaskApi.class);
    private final UserAuthenticationProvider userAuthenticationProvider = mock(UserAuthenticationProvider.class);
    private final AssignTaskService assignTaskService = new AssignTaskService(connectTaskApi, userAuthenticationProvider);

    final String user = "testUser";
    final String taskId = "1";

    @BeforeEach
    void setUp() {
        when(userAuthenticationProvider.getLoggedInUserRoles()).thenReturn(List.of("group1"));
    }

    @Test
    void testAssignUserTask() {
        assignTaskService.assignUserTask(user, taskId, user);

        final ArgumentCaptor<AssignUserTaskCommand> commandCaptor = ArgumentCaptor.forClass(AssignUserTaskCommand.class);
        final ArgumentCaptor<String> userCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<List> rolesCaptor = ArgumentCaptor.forClass(List.class);

        verify(connectTaskApi).assignUserTask(commandCaptor.capture(), userCaptor.capture(), rolesCaptor.capture());

        assertThat(commandCaptor.getValue())
                .hasFieldOrPropertyWithValue("taskId", taskId)
                .hasFieldOrPropertyWithValue("assignee", user);
        assertThat(userCaptor.getValue()).isEqualTo(user);
        assertThat(rolesCaptor.getValue()).contains("group1");
    }

    @Test
    void testUnassignUserTask() {
        assignTaskService.unassignUserTask(user, taskId);

        final ArgumentCaptor<String> taskIdCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> userCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<List> rolesCaptor = ArgumentCaptor.forClass(List.class);

        verify(connectTaskApi).unassignUserTask(taskIdCaptor.capture(), userCaptor.capture(), rolesCaptor.capture());

        assertThat(taskIdCaptor.getValue()).isEqualTo(taskId);
        assertThat(userCaptor.getValue()).isEqualTo(null);
        assertThat(rolesCaptor.getValue()).contains("group1");
    }

}
