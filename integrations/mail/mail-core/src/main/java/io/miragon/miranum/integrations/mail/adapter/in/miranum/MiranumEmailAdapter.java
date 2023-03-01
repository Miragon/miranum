package io.miragon.miranum.integrations.mail.adapter.in.miranum;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailCommand;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiranumEmailAdapter {

    private final SendMailUseCase sendMailUseCase;

    @Worker(type = "sendMail")
    public void sendMail(SendMailCommand sendMailCommand) {
        sendMailUseCase.sendMail(sendMailCommand);
    }
}