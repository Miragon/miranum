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
    public void notifyAssignee(final String assignee, final String eventName, final DelegateTask task) {
        // example implementation
        // you may want to create an email notification here
        log.info("Notify assignee: {} for task: {} and event {}", assignee, task.getName(), eventName);
    }

    @Override
    public void notifyCandidateUsers(final List<String> candidateUsers, final String eventName, final DelegateTask task) {
        // example implementation
        // you may want to create an email notification here
        log.info("Notify candidate users: {} for task: {} and event {}", candidateUsers, task.getName(), eventName);
    }

    @Override
    public void notifyCandidateGroups(final List<String> candidateGroups, final String eventName, final DelegateTask delegateTask) {
        // example implementation
        // you may want to create an email notification here
        log.info("Notify candidate groups: {} for task: {} and event {}", candidateGroups, delegateTask.getName(), eventName);
    }

}
