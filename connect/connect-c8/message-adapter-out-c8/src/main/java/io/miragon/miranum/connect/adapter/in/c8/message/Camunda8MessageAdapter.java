package io.miragon.miranum.connect.adapter.in.c8.message;

import io.camunda.zeebe.client.ZeebeClient;
import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.application.port.out.DeliverMessagePort;
import io.miragon.miranum.connect.message.domain.MessageCorrelationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class Camunda8MessageAdapter implements DeliverMessagePort {

    private final ZeebeClient zeebeClient;

    @Override
    public void deliverMessage(final CorrelateMessageCommand command) throws MessageCorrelationException {

        try {
            this.zeebeClient.newPublishMessageCommand()
                    .messageName(command.getMessageName())
                    .correlationKey(command.getCorrelationKey())
                    .variables(command.getVariables())
                    .send()
                    .join();
        } catch (final Exception error) {
            throw new MessageCorrelationException("Message correlation failed", error);
        }
    }
}
