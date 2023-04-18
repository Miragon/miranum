package io.miragon.miranum.connect.message.impl;

import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;

public interface DeliverMessagePort {

    void deliverMessage(CorrelateMessageCommand command) throws MessageCorrelationException, JsonProcessingException;

}
