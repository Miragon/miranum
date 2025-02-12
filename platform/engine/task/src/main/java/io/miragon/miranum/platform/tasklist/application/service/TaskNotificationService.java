package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.tasklist.application.port.in.TaskNotificationUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskNotificationOutPort;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TaskNotificationService implements TaskNotificationUseCase {

    private final TaskNotificationOutPort taskNotificationOutPort;

    @Override
    public void notifyUsers(final DelegateTask delegateTask) {
        if (delegateTask.getAssignee() != null && !delegateTask.getAssignee().isEmpty()) {
            this.taskNotificationOutPort.notifyAssignee(delegateTask.getAssignee(), delegateTask.getEventName(), delegateTask);
        }
        if (!delegateTask.getCandidates().isEmpty()) {
            final List<String> candidateUsers = delegateTask.getCandidates().stream()
                    .map(IdentityLink::getUserId)
                    .filter(Objects::nonNull)
                    .toList();
            if (!candidateUsers.isEmpty()) {
                this.taskNotificationOutPort.notifyCandidateUsers(candidateUsers, delegateTask.getEventName(), delegateTask);
            }

            final List<String> candidateGroups = delegateTask.getCandidates().stream()
                    .map(IdentityLink::getGroupId)
                    .filter(Objects::nonNull)
                    .toList();
            if (!candidateGroups.isEmpty()) {
                this.taskNotificationOutPort.notifyCandidateGroups(candidateGroups, delegateTask.getEventName(), delegateTask);
            }
        }
    }
}
