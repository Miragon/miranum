package io.miragon.miranum.connect.binder.adapter.scs.message;

import io.miragon.miranum.connect.binder.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.binder.message.application.port.out.DeliverMessagePort;
import io.miragon.miranum.connect.binder.message.domain.MessageCorrelationException;
import lombok.extern.java.Log;

@Log
public class SpringCloudStreamMessageAdapter implements DeliverMessagePort {

    
    @Override
    public void deliverMessage(final CorrelateMessageCommand command) throws MessageCorrelationException {

    }
}
