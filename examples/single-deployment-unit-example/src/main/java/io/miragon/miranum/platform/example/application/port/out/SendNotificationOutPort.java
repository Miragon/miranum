package io.miragon.miranum.platform.example.application.port.out;

import io.miragon.miranum.platform.example.application.domain.SendNotification;

public interface SendNotificationOutPort {

    void notify(final SendNotification sendNotification);

}
