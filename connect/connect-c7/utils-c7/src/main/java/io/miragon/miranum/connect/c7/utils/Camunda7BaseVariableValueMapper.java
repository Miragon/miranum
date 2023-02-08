package io.miragon.miranum.connect.c7.utils;

import org.camunda.community.rest.client.dto.VariableValueDto;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Camunda7BaseVariableValueMapper {

    public Map<String, VariableValueDto> map(final Map<String, Object> variables) {
        return variables.entrySet()
                .stream()
                .map(this::mapVariable)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public VariableValueDto createValue(final Object value) {
        var variableValueDto = new VariableValueDto();
        variableValueDto.setValue(value);
        return variableValueDto;
    }

    private Map.Entry<String, VariableValueDto> mapVariable(final Map.Entry<String, Object> stringObjectEntry) {
        final VariableValueDto value = this.createValue(stringObjectEntry.getValue());
        return new AbstractMap.SimpleEntry<>(stringObjectEntry.getKey(), value);
    }
}