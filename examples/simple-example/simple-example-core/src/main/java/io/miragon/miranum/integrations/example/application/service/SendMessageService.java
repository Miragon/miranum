package io.miragon.miranum.integrations.example.application.service;

import io.miragon.miranum.connect.elementtemplate.api.ElementTemplate;
import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.api.MessageApi;
import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageCommand;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageUseCase;
import io.miragon.miranum.integrations.example.application.port.out.DataQuery;
import io.miragon.miranum.integrations.example.domain.Answer;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class SendMessageService implements SendMessageUseCase {

    private final MessageApi messageApi;

    private final DataQuery dataQuery;

    @Override
    @Worker(type = "sendMessage")
    @ElementTemplate(name = "Send Message", description = "Send a message.")
    public Answer sendMessage(final SendMessageCommand message) {
        log.info("Received message: " + message);
        dataQuery.getDataString();
        dataQuery.getDataStrings();
//        messageApi.correlateMessage(new CorrelateMessageCommand(message.getContent(), message.getKey()));
        return new Answer("answer to: " + message.getContent());
    }
}
