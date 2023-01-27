package io.miragon.miranum.connect.message.application.port.out;

import io.miragon.miranum.connect.binder.message.domain.MessageCorrelationException;
import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageCommand;

public interface DeliverMessagePort {

    void deliverMessage(CorrelateMessageCommand command) throws MessageCorrelationException;

}
