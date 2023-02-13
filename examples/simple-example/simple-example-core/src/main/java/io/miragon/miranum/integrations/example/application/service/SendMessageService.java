package io.miragon.miranum.integrations.example.application.service;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageCommand;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageUseCase;
import io.miragon.miranum.integrations.example.domain.Answer;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class SendMessageService implements SendMessageUseCase {

    @Override
    @Worker(type = "sendMessage")
    public Answer sendMessage(final SendMessageCommand message) {
        log.info("Received message: " + message);
        return new Answer("answer to: " + message.getName());
    }
}
