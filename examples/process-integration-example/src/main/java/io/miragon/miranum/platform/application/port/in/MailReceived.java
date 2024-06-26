package io.miragon.miranum.platform.application.port.in;

public interface MailReceived {
    void handle(CustomerMailCommand command);
}
