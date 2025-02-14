package io.miragon.miranum.platform.example.adapter.in.miranum;

import io.miragon.miranum.platform.example.application.domain.Notification;
import io.miragon.miranum.platform.example.application.port.in.NotificationInPort;
import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskNotificationOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsertaskNotificationAdapter implements TaskNotificationOutPort {

    private final NotificationInPort notificationInPort;

    @Override
    public void notifyAssignee(final String assignee, final String eventName, final DelegateTask task) {
        // example implementation
        notificationInPort.notifyUsers(List.of(assignee), Notification.builder()
            .eventName(eventName)
            .taskName(task.getName())
            .build());
    }

    @Override
    public void notifyCandidateUsers(final List<String> candidateUsers, final String eventName, final DelegateTask task) {
        // example implementation
        notificationInPort.notifyUsers(candidateUsers, Notification.builder()
                .eventName(eventName)
                .taskName(task.getName())
                .build());
    }

    @Override
    public void notifyCandidateGroups(final List<String> candidateGroups, final String eventName, final DelegateTask task) {
        // example implementation
        notificationInPort.notifyGroup(candidateGroups, Notification.builder()
                .eventName(eventName)
                .taskName(task.getName())
                .build());
    }

}
