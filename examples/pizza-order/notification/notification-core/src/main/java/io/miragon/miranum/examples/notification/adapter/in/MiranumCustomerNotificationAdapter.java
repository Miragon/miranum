package io.miragon.miranum.examples.notification.adapter.in;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiranumCustomerNotificationAdapter {

    private final SendMailUseCase sendMailUseCase;

    @Worker(type = "notifyGuest")
    public void notifyCustomer(NotifyGuestCommand notifyGuestCommand) {
        var sendMailCommand = SendMailCommandFactory.create(notifyGuestCommand);
        // Simulate delay
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sendMailUseCase.sendMail(sendMailCommand);
    }
}
