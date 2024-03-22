package io.miranum.platform.connect.c7.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.community.rest.client.dto.VariableValueDto;
import org.springframework.util.ClassUtils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Camunda7RestValueMapper {

    public Map<String, VariableValueDto> map(final Map<String, Object> variables) throws JsonProcessingException {
        Map<String, VariableValueDto> map = new HashMap<>();
        for (Map.Entry<String, Object> stringObjectEntry : variables.entrySet()) {
            Map.Entry<String, VariableValueDto> stringVariableValueDtoEntry = mapVariable(stringObjectEntry);
            if (map.put(stringVariableValueDtoEntry.getKey(), stringVariableValueDtoEntry.getValue()) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        return map;
    }

    public VariableValueDto createValue(final Object value) throws JsonProcessingException {
        var variableValueDto = new VariableValueDto();
        if (Objects.isNull(value) || ClassUtils.isPrimitiveOrWrapper(value.getClass()) || value instanceof String) {
            variableValueDto.setValue(value);
        } else {
            final ObjectMapper objectMapper = new ObjectMapper();
            variableValueDto.setValue(objectMapper.writeValueAsString(value));
            variableValueDto.setType("json");
        }
        return variableValueDto;
    }

    private Map.Entry<String, VariableValueDto> mapVariable(final Map.Entry<String, Object> stringObjectEntry) throws JsonProcessingException {
        final VariableValueDto value = this.createValue(stringObjectEntry.getValue());
        return new AbstractMap.SimpleEntry<>(stringObjectEntry.getKey(), value);
    }


}
