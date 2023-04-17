package io.miragon.miranum.integrations.example.application.service;

import io.miragon.miranum.connect.elementtemplate.api.BPMNElementType;
import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;
import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.api.MessageApi;
import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageCommand;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageUseCase;
import io.miragon.miranum.integrations.example.domain.Answer;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Map;

@Log
@RequiredArgsConstructor
public class SendMessageService implements SendMessageUseCase {

    private final MessageApi messageApi;

    @Override
    @Worker(type = "sendMessage")
    @GenerateElementTemplate(name = "Send Message",
            id = "send-message",
            type = "sendMessage",
            appliesTo = {BPMNElementType.BPMN_SERVICE_TASK},
            version = "0-1")
    public Answer sendMessage(final SendMessageCommand message) {
        log.info("Received message: " + message);

        messageApi.correlateMessage(new CorrelateMessageCommand(message.getName(), message.getKey(), Map.of()));

        return new Answer("answer to: " + message.getName());
    }
}