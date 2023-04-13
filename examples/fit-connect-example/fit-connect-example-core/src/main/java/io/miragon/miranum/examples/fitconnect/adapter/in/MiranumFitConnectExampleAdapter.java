package io.miragon.miranum.examples.fitconnect.adapter.in;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailCommand;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiranumFitConnectExampleAdapter {

    private final SendMailUseCase sendMailUseCase;

    @Worker(type = "sendNotification")
    public void sendNotification(UserInput userInput) {
        var sendMailCommand = new SendMailCommand(
                String.format("%s.%s%s@gmail.com", userInput.getFirstname(), userInput.getAge(), userInput.getLastname()),
                "Test notification",
                "Fit-Connect-Process test notification."
        );
        sendMailUseCase.sendMail(sendMailCommand);
    }
}