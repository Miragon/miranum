package io.miragon.miranum.platform.tasklist.adapter.out.task;

import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskNotificationOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class TaskNotificationAdapter implements TaskNotificationOutPort {

    @Override
    public void notifyAssignee(final String assignee, final String eventName, final DelegateTask task) {
        log.warn("Notify users for task: {} is not possible. Make sure to implement a custom TaskNotificationOutPort or disable task notifications", task.getName());
    }

    @Override
    public void notifyCandidateUsers(final List<String> candidateUsers, final String eventName, final DelegateTask task) {
        log.warn("Notify users for task: {} is not possible. Make sure to implement a custom TaskNotificationOutPort or disable task notifications", task.getName());
    }

    @Override
    public void notifyCandidateGroups(final List<String> candidateGroups, final String eventName, final DelegateTask task) {
        log.warn("Notify users for task: {} is not possible. Make sure to implement a custom TaskNotificationOutPort or disable task notifications", task.getName());
    }
}
