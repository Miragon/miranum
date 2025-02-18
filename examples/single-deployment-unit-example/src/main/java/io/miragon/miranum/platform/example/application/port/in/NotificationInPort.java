package io.miragon.miranum.platform.example.application.port.in;

import io.miragon.miranum.platform.example.application.domain.Notification;

import java.util.List;

public interface NotificationInPort {

    void notifyUsers(final List<String> users, final Notification notification);

    void notifyGroup(final List<String> groups, final Notification notification);

}
