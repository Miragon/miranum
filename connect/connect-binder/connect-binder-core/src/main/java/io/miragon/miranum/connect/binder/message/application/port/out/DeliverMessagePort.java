package io.miragon.miranum.connect.binder.message.application.port.out;

import io.miragon.miranum.connect.binder.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.binder.message.domain.MessageCorrelationException;

public interface DeliverMessagePort {

    void deliverMessage(CorrelateMessageCommand command) throws MessageCorrelationException;

}
