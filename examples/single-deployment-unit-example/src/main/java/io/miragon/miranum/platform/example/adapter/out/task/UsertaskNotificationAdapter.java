package io.miragon.miranum.platform.example.adapter.out.task;

import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskNotificationOutPort;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UsertaskNotificationAdapter implements TaskNotificationOutPort {

    @Override
    public void notifyAssignee(final String assignee, final DelegateTask task) {
        // example implementation
        // you may want to create an email notification here
        log.info("Notify assignee: {} for task: {}", assignee, task.getName());
    }

    @Override
    public void notifyCandidateUsers(final List<String> candidateUsers, final DelegateTask task) {
        // example implementation
        // you may want to create an email notification here
        log.info("Notify candidate users: {} for task: {}", candidateUsers, task.getName());
    }

    @Override
    public void notifyCandidateGroups(final List<String> candidateGroups, final DelegateTask delegateTask) {
        // example implementation
        // you may want to create an email notification here
        log.info("Notify candidate groups: {} for task: {}", candidateGroups, delegateTask.getName());
    }

}
