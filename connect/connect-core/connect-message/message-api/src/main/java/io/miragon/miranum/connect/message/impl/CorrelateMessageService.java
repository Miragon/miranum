package io.miragon.miranum.connect.message.impl;

import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.api.CorrelateMessageUseCase;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CorrelateMessageService implements CorrelateMessageUseCase {

    private final DeliverMessagePort deliverMessagePort;

    @Override
    public void correlateMessage(final CorrelateMessageCommand command) {
        this.deliverMessagePort.deliverMessage(command);
    }
}
