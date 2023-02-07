package io.miragon.miranum.integrations.mail.application.port.in;

public interface SendMailUseCase {

    void sendMail(SendMailCommand command);

}
