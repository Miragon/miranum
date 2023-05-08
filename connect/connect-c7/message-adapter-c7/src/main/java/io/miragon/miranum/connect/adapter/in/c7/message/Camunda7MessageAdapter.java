package io.miragon.miranum.connect.adapter.in.c7.message;

import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.impl.DeliverMessagePort;
import io.miragon.miranum.connect.message.impl.MessageCorrelationException;
import lombok.RequiredArgsConstructor;
import org.camunda.community.rest.client.api.MessageApi;
import org.camunda.community.rest.client.dto.CorrelationMessageDto;

@RequiredArgsConstructor
public class Camunda7MessageAdapter implements DeliverMessagePort {

    private final Camunda7CorrelateMessageMapper mapper;
    private final MessageApi messageApi;

    @Override
    public void deliverMessage(final CorrelateMessageCommand command) throws MessageCorrelationException {
        try {
            final CorrelationMessageDto dto = this.mapper.map(command);
            this.messageApi.deliverMessage(dto);
        } catch (final Exception e) {
            throw new MessageCorrelationException("Message correlation failed.", e);
        }
    }
}
