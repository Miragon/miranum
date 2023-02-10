package io.miragon.miranum.connect.message.application.port.out;

import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.domain.MessageCorrelationException;

public interface DeliverMessagePort {

    void deliverMessage(CorrelateMessageCommand command) throws MessageCorrelationException;

}
