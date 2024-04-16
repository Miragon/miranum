package io.miragon.miranum.platform.connect.message.message.impl;

import io.miragon.miranum.platform.connect.message.message.api.CorrelateMessageCommand;

public interface DeliverMessagePort {

    void deliverMessage(CorrelateMessageCommand command) throws MessageCorrelationException;

}
