package io.miranum.platform.tasklist.application.service;

import com.google.common.collect.Sets;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.auth.User;
import io.miranum.platform.tasklist.adapter.out.schema.VariableTaskSchemaResolverAdapter;
import io.miranum.platform.tasklist.adapter.out.schema.VariableTaskSchemaTypeResolverAdapter;
import io.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miranum.platform.tasklist.application.port.out.cancellation.CancellationFlagOutPort;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskOutPort;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaRefResolverPort;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaTypeResolverPort;
import io.miranum.platform.tasklist.application.port.out.security.CurrentUserPort;
import io.miranum.platform.tasklist.domain.PageOfTasks;
import io.miranum.platform.tasklist.domain.PagingAndSorting;
import io.miranum.platform.tasklist.domain.TaskSchemaType;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.miranum.platform.tasklist.adapter.out.schema.TaskVariables.TASK_SCHEMA_TYPE;
import static io.miranum.platform.tasklist.application.service.TestFixtures.generateTask;
import static io.miranum.platform.tasklist.application.service.TestFixtures.generateTasks;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RetrieveTasksForUserUseCaseTest {

    private final TaskOutPort taskQueryPort = mock(TaskOutPort.class);
    private final CurrentUserPort currentUserPort = mock(CurrentUserPort.class);

    private final CancellationFlagOutPort cancellationFlagOutPort = mock(CancellationFlagOutPort.class);
    private final TaskSchemaRefResolverPort taskSchemaRefResolverPort = new VariableTaskSchemaResolverAdapter();
    private final TaskSchemaTypeResolverPort taskSchemaTypeResolverPort = new VariableTaskSchemaTypeResolverAdapter();

    private final UserTaskQuery useCase = new UserTaskQueryImpl(
            taskQueryPort,
            currentUserPort,
            taskSchemaRefResolverPort,
            cancellationFlagOutPort,
            taskSchemaTypeResolverPort
    );

    private final String query = "";
    private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));
    private final PagingAndSorting pagingAndSorting = new PagingAndSorting(0, 100, "");

    @BeforeEach
    void setupMocks() {
        when(currentUserPort.getCurrentUser()).thenReturn(user);
        when(cancellationFlagOutPort.apply(any())).thenReturn(true);
    }

    @Test
    void getsUnassignedTasksForCurrentUserGroupAndCandidate() {

        val content = generateTasks(11, Sets.newHashSet(), Sets.newHashSet("group1"), null);
        content.addAll(generateTasks(6, Sets.newHashSet("0123456789", "987654321"), Sets.newHashSet(), null));

        val pageOfTasks = new PageOfTasks(content, 17, pagingAndSorting);

        when(taskQueryPort.getTasksForCurrentUserGroup(any(), anyString(), anyBoolean(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getUnassignedTasksForCurrentUserGroup(query, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
        verify(taskQueryPort).getTasksForCurrentUserGroup(user, query, false, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }


    @Test
    void getsUnassignedTasksForCurrentUserGroup() {

        val pageOfTasks = new PageOfTasks(
                generateTasks(17, Sets.newHashSet(), Sets.newHashSet("group1"), null),
                17,
                pagingAndSorting
        );

        when(taskQueryPort.getTasksForCurrentUserGroup(any(), anyString(), anyBoolean(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getUnassignedTasksForCurrentUserGroup(query, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
        verify(taskQueryPort).getTasksForCurrentUserGroup(user, query, false, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }

    @Test
    void getsAssignedTasksForCurrentUserGroup() {

        val pageOfTasks = new PageOfTasks(
                generateTasks(17, Sets.newHashSet(), Sets.newHashSet("group1"), null),
                17,
                pagingAndSorting
        );

        when(taskQueryPort.getTasksForCurrentUserGroup(any(), anyString(), anyBoolean(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getAssignedTasksForCurrentUserGroup(query, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
        verify(taskQueryPort).getTasksForCurrentUserGroup(user, query, true, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }

    @Test
    void getsTasksForCurrentUser() {

        val pageOfTasks = new PageOfTasks(
                generateTasks(17, Sets.newHashSet(), Sets.newHashSet(), user.getUsername()),
                17,
                pagingAndSorting
        );

        when(taskQueryPort.getTasksForCurrentUser(any(), anyString(), any(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getTasksForCurrentUser(query, null, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(17);
        verify(taskQueryPort).getTasksForCurrentUser(user, query, null, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }

    @Test
    void getsLegacyAndSchemaBasedTasksForCurrentUser() {

        val allTasks = generateTasks(15, Sets.newHashSet(), Sets.newHashSet(), user.getUsername());
        allTasks.add(generateTask("task_15", Sets.newHashSet(), Sets.newHashSet(), user.getUsername(), null, true,
                CamundaBpmData.builder().set(TASK_SCHEMA_TYPE, TaskSchemaType.SCHEMA_BASED).build())
        );

        val pageOfTasks = new PageOfTasks(
                allTasks,
                15,
                pagingAndSorting
        );

        when(taskQueryPort.getTasksForCurrentUser(any(), anyString(), any(), any())).thenReturn(pageOfTasks);

        val tasks = useCase.getTasksForCurrentUser(query, null, pagingAndSorting);
        assertThat(tasks.getTotalElementsCount()).isEqualTo(15);
        verify(taskQueryPort).getTasksForCurrentUser(user, query, null, pagingAndSorting);
        verifyNoMoreInteractions(taskQueryPort);
    }

}
