package io.miragon.miranum.examples.pizzaorder.email;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiranumCustomerNotificationAdapter {

    private final SendMailUseCase sendMailUseCase;

    @Worker(type = "notifyGuest")
    public void notifyCustomer(NotifyGuestCommand notifyGuestCommand) {
        var sendMailCommand = SendMailCommandFactory.create(notifyGuestCommand);
        sendMailUseCase.sendMail(sendMailCommand);
    }
}
