package io.miragon.miranum.examples.process.application.service;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.examples.process.application.port.in.SendMessageCommand;
import io.miragon.miranum.examples.process.application.port.in.SendMessageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class SendMessageService implements SendMessageUseCase {

    @Override
    @Worker(type = "sendMessage")
    public void sendMessage(final SendMessageCommand message) {
        log.info("Received message: " + message);
    }
}
