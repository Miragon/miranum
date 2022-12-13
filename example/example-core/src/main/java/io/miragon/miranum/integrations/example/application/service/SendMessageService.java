package io.miragon.miranum.integrations.example.application.service;

import io.miragon.miranum.connect.binder.domain.UseCase;
import io.miragon.miranum.integrations.example.Answer;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageCommand;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageUseCase;
import lombok.extern.java.Log;

@Log
public class SendMessageService implements SendMessageUseCase {

    @Override
    @UseCase(type = "sendMessage")
    public Answer sendMessage(final SendMessageCommand message) {
        log.info("Received message: " + message.getValue());
        return new Answer("answer to: " + message.getValue());
    }
}
