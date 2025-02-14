package io.miragon.miranum.platform.example.application;

import io.miragon.miranum.platform.example.application.domain.Notification;
import io.miragon.miranum.platform.example.application.domain.SendNotification;
import io.miragon.miranum.platform.example.application.port.in.NotificationInPort;
import io.miragon.miranum.platform.example.application.port.out.SendNotificationOutPort;
import io.miragon.miranum.platform.user.application.port.in.UserApi;
import io.miragon.miranum.platform.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UsertaskNotificationUseCase implements NotificationInPort {

    private final SendNotificationOutPort sendNotificationOutPort;
    private final UserApi userApi;

    @Override
    public void notifyUsers(final List<String> users, final Notification notification) {
        // don't send notification for create and complete events
        if(notification.getEventName().equalsIgnoreCase("create") || notification.getEventName().equalsIgnoreCase("complete")) {
            return;
        }

        final List<User> receivers = new ArrayList<>();
        users.forEach(user -> receivers.add(userApi.getUserByUserName(user)));

        sendNotificationOutPort.notify(SendNotification.builder()
            .receivers(receivers.stream().map(User::getEmail).toList())
            .subject(notification.getTitle())
            .body(notification.getMessage())
            .build());
    }

    @Override
    public void notifyGroup(final List<String> groups, final Notification notification) {
        // don't send notification for assignment events
        if (notification.getEventName().equalsIgnoreCase("assignment")) {
            return;
        }

        final List<User> receivers = new ArrayList<>();
        groups.forEach(group -> receivers.addAll(userApi.getUsersByGroup(group)));

        sendNotificationOutPort.notify(SendNotification.builder()
            .receivers(receivers.stream().map(User::getEmail).toList())
            .subject(notification.getTitle())
            .body(notification.getMessage())
            .build());
    }
}
