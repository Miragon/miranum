package io.miragon.miranum.connect.adapter.in.c7.message;

import io.miragon.miranum.connect.c7.utils.Camunda7BaseVariableValueMapper;
import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import lombok.AllArgsConstructor;
import org.camunda.community.rest.client.dto.CorrelationMessageDto;
import org.camunda.community.rest.client.dto.VariableValueDto;

import java.util.Map;

@AllArgsConstructor
public class Camunda7CorrelateMessageMapper {

    private final Camunda7BaseVariableValueMapper camunda7BaseVariableValueMapper;

    public CorrelationMessageDto map(final CorrelateMessageCommand command) {

        final CorrelationMessageDto correlationMessageDto = new CorrelationMessageDto();
        correlationMessageDto.setMessageName(command.getMessageName());

        final VariableValueDto correlationKey = this.camunda7BaseVariableValueMapper.createValue(command.getCorrelationKey());
        correlationMessageDto.setLocalCorrelationKeys(Map.of("correlationKey", correlationKey));
        correlationMessageDto.setProcessVariables(this.camunda7BaseVariableValueMapper.map(command.getVariables()));
        return correlationMessageDto;
    }
}
