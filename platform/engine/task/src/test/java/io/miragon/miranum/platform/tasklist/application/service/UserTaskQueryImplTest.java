package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserTaskQueryImplTest {

    private final TaskOutPort taskOutPort = mock(TaskOutPort.class);
    private final UserTaskQuery userTaskQuery = new UserTaskQueryImpl(taskOutPort);

    private final List<Task> exampleTasks = List.of(
        Task.builder()
            .id("1")
            .name("Example Task")
            .description("This is an example task")
            .processName("Example Process")
            .processInstanceId("1")
            .candidateGroups("group1")
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
            .candidateGroups("group1")
            .form("exampleForm")
            .build()
    );

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

        when(taskOutPort.getTask(user, taskId)).thenReturn(tasks);

        final Task result = userTaskQuery.getTask(user, taskId);

        assertThat(result)
                .isNotNull()
                .isEqualTo(exampleTasks.get(0));
    }

}
