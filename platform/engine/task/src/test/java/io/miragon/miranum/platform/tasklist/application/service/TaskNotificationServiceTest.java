package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.tasklist.application.port.in.TaskNotificationUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskNotificationOutPort;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskNotificationServiceTest {


    private final TaskNotificationOutPort taskNotificationOutPort = mock(TaskNotificationOutPort.class);
    private final TaskNotificationUseCase taskNotificationService = new TaskNotificationService(taskNotificationOutPort);

    private final DelegateTask delegateTask = mock(DelegateTask.class);

    @Test
    void testNotifyUsers_withAssignee() {
        when(delegateTask.getAssignee()).thenReturn("assigneeUser");

        taskNotificationService.notifyUsers(delegateTask);

        final ArgumentCaptor<String> assigneeCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<DelegateTask> taskCaptor = ArgumentCaptor.forClass(DelegateTask.class);

        verify(taskNotificationOutPort).notifyAssignee(assigneeCaptor.capture(), taskCaptor.capture());

        assertThat(assigneeCaptor.getValue())
                .isEqualTo("assigneeUser");
        assertThat(taskCaptor.getValue())
                .isEqualTo(delegateTask);
    }

    @Test
    void testNotifyUsers_withCandidateUsers() {
        final IdentityLink candidateUser = mock(IdentityLink.class);
        when(candidateUser.getUserId()).thenReturn("candidateUser");
        when(delegateTask.getCandidates()).thenReturn(Set.of(candidateUser));

        taskNotificationService.notifyUsers(delegateTask);

        final ArgumentCaptor<List<String>> candidateUsersCaptor = ArgumentCaptor.forClass(List.class);
        final ArgumentCaptor<DelegateTask> taskCaptor = ArgumentCaptor.forClass(DelegateTask.class);

        verify(taskNotificationOutPort).notifyCandidateUsers(candidateUsersCaptor.capture(), taskCaptor.capture());

        assertThat(candidateUsersCaptor.getValue()).containsExactly("candidateUser");
        assertThat(taskCaptor.getValue()).isEqualTo(delegateTask);
    }

    @Test
    void testNotifyUsers_withCandidateGroups() {
        final IdentityLink candidateGroup = mock(IdentityLink.class);
        when(candidateGroup.getGroupId()).thenReturn("candidateGroup");
        when(delegateTask.getCandidates()).thenReturn(Set.of(candidateGroup));

        taskNotificationService.notifyUsers(delegateTask);

        final ArgumentCaptor<List<String>> candidateGroupsCaptor = ArgumentCaptor.forClass(List.class);
        final ArgumentCaptor<DelegateTask> taskCaptor = ArgumentCaptor.forClass(DelegateTask.class);

        verify(taskNotificationOutPort).notifyCandidateGroups(candidateGroupsCaptor.capture(), taskCaptor.capture());

        assertThat(candidateGroupsCaptor.getValue())
                .containsExactly("candidateGroup");
        assertThat(taskCaptor.getValue())
                .isEqualTo(delegateTask);
    }

}
