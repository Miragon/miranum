package io.miragon.miranum.platform.connect.message.message.impl;

import io.miragon.miranum.platform.connect.message.message.api.CorrelateMessageCommand;
import io.miragon.miranum.platform.connect.message.message.api.MessageApi;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MessageService implements MessageApi {

    private final DeliverMessagePort deliverMessagePort;

    @Override
    public void correlateMessage(final CorrelateMessageCommand command) {
        this.deliverMessagePort.deliverMessage(command);
    }
}
