package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.application.accesscontrol.UserTaskAccessProvider;
import io.miragon.miranum.platform.tasklist.application.accesscontrol.UserTaskAccessProviderImpl;
import io.miragon.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserTaskQueryImplTest {

    private final TaskOutPort taskOutPort = mock(TaskOutPort.class);
    private final UserAuthenticationProvider authenticationProvider = mock(UserAuthenticationProvider.class);
    private final UserTaskAccessProvider userTaskAccessProvider = new UserTaskAccessProviderImpl(authenticationProvider);
    private final UserTaskQuery userTaskQuery = new UserTaskQueryImpl(taskOutPort, userTaskAccessProvider);

    private final List<Task> exampleTasks = List.of(
        Task.builder()
            .id("1")
            .name("Example Task")
            .description("This is an example task")
            .processName("Example Process")
            .processInstanceId("1")
            .candidateGroups(List.of("group1"))
            .form("exampleForm")
            .build(),
        Task.builder()
            .id("2")
            .name("Example Task 2")
            .description("This is another example task")
            .processName("Example Process")
            .processInstanceId("2")
            .assignee("user1")
            .form("exampleForm")
            .build(),
        Task.builder()
            .id("3")
            .name("Example Task 3")
            .description("This is another example task")
            .processName("Example Process")
            .processInstanceId("3")
            .assignee("user1")
            .candidateGroups(List.of("group1"))
            .form("exampleForm")
            .build()
    );

    @BeforeEach
    void setUp() {
        when(authenticationProvider.getLoggedInUserRoles()).thenReturn(List.of("group1"));
    }

    @Test
    void test_get_all_tasks_for_candidate_group() {
        final String user = "user1";
        final String group = "group1";
        final List<Task> tasks = exampleTasks.stream()
            .filter(task -> task.getCandidateGroups() != null && task.getCandidateGroups().contains(group))
            .toList();

        when(taskOutPort.getTasksForUserGroup(group, user)).thenReturn(tasks);

        final List<Task> result = userTaskQuery.getTasksForUserGroup(group, user);

        assertThat(result)
            .isNotNull()
            .hasSize(2)
            .containsExactlyInAnyOrder(
                exampleTasks.get(0),
                exampleTasks.get(2)
            );
    }

    @Test
    void test_get_all_tasks_for_user() {
        final String user = "user1";
        final List<Task> tasks = exampleTasks.stream()
                .filter(task -> task.getAssignee() != null && task.getAssignee().equals(user))
                .toList();

        when(taskOutPort.getTasksForUser(user)).thenReturn(tasks);

        final List<Task> result = userTaskQuery.getTasksForUser(user);

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        exampleTasks.get(1),
                        exampleTasks.get(2)
                );
    }

    @Test
    void test_get_task_by_id() {
        final String user = "user1";
        final String taskId = "1";
        final Task tasks = exampleTasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findAny()
                .orElse(null);

        when(taskOutPort.getTask(taskId)).thenReturn(tasks);

        final Task result = userTaskQuery.getTask(user, taskId);

        assertThat(result)
                .isNotNull()
                .isEqualTo(exampleTasks.get(0));
    }

    @Test
    void test_get_task_by_id_with_no_access() {
        final String user = "notAssignedUser";
        final String taskId = "5";
        when(taskOutPort.getTask(taskId)).thenReturn(Task.builder()
                .id("5")
                .name("Example Task 5")
                .description("This is another example task")
                .processName("Example Process")
                .processInstanceId("5")
                .assignee("user5")
                .candidateGroups(List.of("group3"))
                .form("exampleForm")
                .build());

        assertThatThrownBy(() -> userTaskQuery.getTask(user, taskId))
                .isInstanceOf(TaskAccessDeniedException.class)
                .hasMessage("User notAssignedUser has no access to task 5");
    }
}
