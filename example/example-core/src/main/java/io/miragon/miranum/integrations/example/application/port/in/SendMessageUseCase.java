package io.miragon.miranum.integrations.example.application.port.in;

import io.miragon.miranum.integrations.example.Answer;

public interface SendMessageUseCase {

    Answer sendMessage(final SendMessageCommand command);
}
