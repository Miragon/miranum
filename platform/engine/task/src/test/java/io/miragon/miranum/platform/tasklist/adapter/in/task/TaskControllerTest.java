package io.miragon.miranum.platform.tasklist.adapter.in.task;

import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.adapter.in.task.dto.AssignTaskDto;
import io.miragon.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miragon.miranum.platform.tasklist.application.port.in.WorkOnTaskUseCase;
import io.miragon.miranum.platform.tasklist.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private final UserTaskQuery userTaskQuery = mock(UserTaskQuery.class);
    private final UserAuthenticationProvider authenticationProvider = mock(UserAuthenticationProvider.class);
    private final WorkOnTaskUseCase workOnTaskUseCase = mock(WorkOnTaskUseCase.class);
    private final TaskController taskController = new TaskController(userTaskQuery, workOnTaskUseCase, authenticationProvider);

    private final String loggedInUser = "testUser";
    private final List<Task> exampleTasks = List.of(
            Task.builder()
                    .id("1")
                    .name("Example Task")
                    .description("This is an example task")
                    .processName("Example Process")
                    .processInstanceId("1")
                    .candidateGroups(List.of("group1"))
                    .formKey("exampleForm")
                    .build(),
            Task.builder()
                    .id("2")
                    .name("Example Task 2")
                    .description("This is another example task")
                    .processName("Example Process")
                    .processInstanceId("2")
                    .assignee(loggedInUser)
                    .formKey("exampleForm")
                    .build(),
            Task.builder()
                    .id("3")
                    .name("Example Task 3")
                    .description("This is another example task")
                    .processName("Example Process")
                    .processInstanceId("3")
                    .assignee(loggedInUser)
                    .candidateGroups(List.of("group1"))
                    .formKey("exampleForm")
                    .build()
    );

    @BeforeEach
    void setUp() {
        when(authenticationProvider.getLoggedInUser()).thenReturn(loggedInUser);
    }

    @Test
    void test_get_tasks_for_current_user() {
        final List<Task> expectedTasks = exampleTasks.stream()
                .filter(task -> task.getAssignee() != null && task.getAssignee().equals(loggedInUser))
                .toList();
        when(userTaskQuery.getTasksForUser(loggedInUser)).thenReturn(expectedTasks);

        final List<Task> result = taskController.getUserTasksForCurrentUser();

        assertThat(result)
            .isNotNull()
            .hasSize(2)
            .containsExactlyInAnyOrder(
                    exampleTasks.get(1),
                    exampleTasks.get(2)
            );
    }

    @Test
    void test_get_tasks_for_group() {
        final String group = "group1";
        final List<Task> expectedTasks = exampleTasks.stream()
                .filter(task -> task.getCandidateGroups() != null && task.getCandidateGroups().contains(group))
                .toList();
        when(userTaskQuery.getTasksForUserGroup(loggedInUser, group)).thenReturn(expectedTasks);

        final List<Task> result = taskController.getTasksForGroup(group);

        assertThat(result)
            .isNotNull()
            .hasSize(2)
            .containsExactlyInAnyOrder(
                    exampleTasks.get(0),
                    exampleTasks.get(2)
            );
    }

    @Test
    void test_get_task_by_id() {
        final String taskId = "1";
        final Task expectedTask = exampleTasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElseThrow();
        when(userTaskQuery.getTask(taskId, loggedInUser)).thenReturn(expectedTask);

        final Task result = taskController.getTask(taskId);

        assertThat(result).isEqualTo(expectedTask);
    }

    @Test
    void test_assign_task() {
        final String taskId = "1";
        final String assignee = "testAssignee";
        final AssignTaskDto assignTaskDto = new AssignTaskDto(assignee);
        taskController.assignTask(taskId, assignTaskDto);
        verify(workOnTaskUseCase).assignUserTask(loggedInUser, taskId, assignee);
    }

    @Test
    void test_unasign_task() {
        final String taskId = "1";
        taskController.unassignTask(taskId);
        verify(workOnTaskUseCase).unassignUserTask(loggedInUser, taskId);
    }
}
