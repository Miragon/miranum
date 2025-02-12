package io.miragon.miranum.platform.tasklist.application.port.out.task;

import org.camunda.bpm.engine.delegate.DelegateTask;

import java.util.List;

public interface TaskNotificationOutPort {

    void notifyAssignee(final String assignee, final String eventName, final DelegateTask task);

    void notifyCandidateUsers(final List<String> candidateUsers, final String eventName, final DelegateTask task);

    void notifyCandidateGroups(final List<String> candidateGroups, final String eventName, final DelegateTask delegateTask);

}
