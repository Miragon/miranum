package io.miragon.miranum.integrations.mail.application.service;

import io.miragon.miranum.integrations.mail.application.port.in.SendMailCommand;
import io.miragon.miranum.integrations.mail.application.port.in.SendMailUseCase;
import io.miragon.miranum.integrations.mail.application.port.out.DeliverMailPort;
import io.miragon.miranum.integrations.mail.domain.Mail;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendMailService implements SendMailUseCase {

    private final DeliverMailPort deliverMailPort;

    @Override
    public void sendMail(final SendMailCommand command) {
        // TODO: Load and fill out templates
        this.deliverMailPort.deliverMail(new Mail(command.getText(), command.getSubject(), command.getRecipient()));
    }
}