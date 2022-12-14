package io.miragon.miranum.integrations.example.application.service;

import io.miragon.miranum.connect.binder.domain.UseCase;
import io.miragon.miranum.integrations.example.application.port.in.FireAndForgetUseCase;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageCommand;
import lombok.extern.java.Log;

@Log
public class FireAndForgetService implements FireAndForgetUseCase {

    @Override
    @UseCase(type = "fireAndForget")
    public void fireAndForget(final SendMessageCommand message) {
        log.info("Received message: " + message.getValue());
    }
}
