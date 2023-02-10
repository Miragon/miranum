package io.miragon.miranum.examples.process.application.port.in;


public interface SendMessageUseCase {

    void sendMessage(final SendMessageCommand command);
}
