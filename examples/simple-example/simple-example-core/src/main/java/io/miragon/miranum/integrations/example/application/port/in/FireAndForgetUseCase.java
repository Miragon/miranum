package io.miragon.miranum.integrations.example.application.port.in;


public interface FireAndForgetUseCase {

    void fireAndForget(final SendMessageCommand command);
}
