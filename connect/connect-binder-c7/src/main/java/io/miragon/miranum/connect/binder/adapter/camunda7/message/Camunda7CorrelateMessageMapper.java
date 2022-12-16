package io.miragon.miranum.connect.binder.adapter.camunda7.message;

import io.miragon.miranum.connect.binder.message.application.port.in.CorrelateMessageCommand;
import org.camunda.community.rest.client.dto.CorrelationMessageDto;
import org.camunda.community.rest.client.dto.VariableValueDto;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Camunda7CorrelateMessageMapper {

    public CorrelationMessageDto map(final CorrelateMessageCommand command) {

        final CorrelationMessageDto correlationMessageDto = new CorrelationMessageDto();
        correlationMessageDto.setMessageName(command.getMessageName());

        final VariableValueDto correlationKey = this.createValue(command.getCorrelationKey());
        correlationMessageDto.setLocalCorrelationKeys(Map.of("correlationKey", correlationKey));
        correlationMessageDto.setProcessVariables(this.map(command.getVariables()));
        return correlationMessageDto;
    }

    public Map<String, VariableValueDto> map(final Map<String, Object> variables) {
        return variables.entrySet()
                .stream()
                .map(this::mapVariable)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, VariableValueDto> mapVariable(final Map.Entry<String, Object> stringObjectEntry) {
        final VariableValueDto value = this.createValue(stringObjectEntry.getValue());
        return new AbstractMap.SimpleEntry<>(stringObjectEntry.getKey(), value);
    }

    private VariableValueDto createValue(final Object value) {
        final VariableValueDto correlationKey = new VariableValueDto();
        correlationKey.setValue(value);
        return correlationKey;
    }


}
