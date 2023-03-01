package io.miragon.miranum.examples.fitconnect.adapter.in;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailCommand;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiranumFitConnectExampleAdapter {

    private final SendMailUseCase sendMailUseCase;

    @Worker(type = "sendMail")
    public void sendMail(SendMailCommand sendMailCommand) {
        sendMailUseCase.sendMail(sendMailCommand);
    }
}