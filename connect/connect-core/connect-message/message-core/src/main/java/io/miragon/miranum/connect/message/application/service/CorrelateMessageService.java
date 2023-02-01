package io.miragon.miranum.connect.message.application.service;

import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageUseCase;
import io.miragon.miranum.connect.message.application.port.out.DeliverMessagePort;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CorrelateMessageService implements CorrelateMessageUseCase {

    private final DeliverMessagePort deliverMessagePort;

    @Override
    public void correlateMessage(CorrelateMessageCommand command) {
        deliverMessagePort.deliverMessage(command);
    }
}
