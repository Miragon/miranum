package io.miragon.miranum.platform.tasklist.application.port.out.task;

import org.camunda.bpm.engine.delegate.DelegateTask;

import java.util.List;

public interface TaskNotificationOutPort {

    void notifyAssignee(final String assignee, final DelegateTask task);

    void notifyCandidateUsers(final List<String> candidateUsers, final DelegateTask task);

    void notifyCandidateGroups(final List<String> candidateGroups, final DelegateTask delegateTask);

}
