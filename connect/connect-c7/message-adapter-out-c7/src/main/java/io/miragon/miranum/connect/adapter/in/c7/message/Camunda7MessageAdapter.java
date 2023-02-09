package io.miragon.miranum.connect.adapter.in.c7.message;

import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.application.port.out.DeliverMessagePort;
import io.miragon.miranum.connect.message.domain.MessageCorrelationException;
import lombok.RequiredArgsConstructor;
import org.camunda.community.rest.client.api.MessageApi;
import org.camunda.community.rest.client.dto.CorrelationMessageDto;
import org.camunda.community.rest.client.invoker.ApiException;

@RequiredArgsConstructor
public class Camunda7MessageAdapter implements DeliverMessagePort {

    private final Camunda7CorrelateMessageMapper mapper;
    private final MessageApi messageApi;

    @Override
    public void deliverMessage(final CorrelateMessageCommand command) throws MessageCorrelationException {

        final CorrelationMessageDto dto = this.mapper.map(command);

        try {
            this.messageApi.deliverMessage(dto);
        } catch (final ApiException e) {
            throw new MessageCorrelationException("Message correlation failed.", e);
        }
    }
}
