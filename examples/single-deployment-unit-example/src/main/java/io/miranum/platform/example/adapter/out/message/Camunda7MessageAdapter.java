package io.miranum.platform.example.adapter.out.message;

import io.miranum.platform.connect.message.api.CorrelateMessageCommand;
import io.miranum.platform.connect.message.impl.DeliverMessagePort;
import io.miranum.platform.connect.message.impl.MessageCorrelationException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Camunda7MessageAdapter implements DeliverMessagePort {

    private final RuntimeService runtimeService;

    @Override
    public void deliverMessage(final CorrelateMessageCommand command) throws MessageCorrelationException {
        runtimeService.correlateMessage(command.getMessageName(), command.getCorrelationKey(), command.getVariables());
    }
}
