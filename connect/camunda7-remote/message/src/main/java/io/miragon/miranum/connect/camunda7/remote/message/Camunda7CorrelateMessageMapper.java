package io.miragon.miranum.connect.camunda7.remote.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.miragon.miranum.connect.camnda7.remote.utils.Camunda7RestValueMapper;
import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import lombok.AllArgsConstructor;
import org.camunda.community.rest.client.dto.CorrelationMessageDto;

@AllArgsConstructor
public class Camunda7CorrelateMessageMapper {

    private final Camunda7RestValueMapper camunda7BaseVariableValueMapper;

    public CorrelationMessageDto map(final CorrelateMessageCommand command) throws JsonProcessingException {

        final CorrelationMessageDto correlationMessageDto = new CorrelationMessageDto();
        correlationMessageDto.setMessageName(command.getMessageName());
        correlationMessageDto.setBusinessKey(command.getCorrelationKey());
        correlationMessageDto.setProcessVariables(this.camunda7BaseVariableValueMapper.map(command.getVariables()));
        return correlationMessageDto;
    }
}
