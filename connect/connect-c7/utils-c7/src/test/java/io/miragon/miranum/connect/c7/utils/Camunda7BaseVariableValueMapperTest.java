package io.miragon.miranum.connect.c7.utils;

import org.camunda.community.rest.client.dto.VariableValueDto;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Camunda7BaseVariableValueMapperTest {

    private final Camunda7BaseVariableValueMapper mapper = new Camunda7BaseVariableValueMapper();

    @Test
    public void testMap_ShouldReturnMappedVariables() {
        // Given
        Map<String, Object> variables = new HashMap<>();
        variables.put("var1", "value1");
        variables.put("var2", 2);

        // When
        Map<String, VariableValueDto> result = mapper.map(variables);

        // Then
        assertEquals(2, result.size());
        assertEquals("value1", result.get("var1").getValue());
        assertEquals(2, result.get("var2").getValue());
    }

    @Test
    public void testMap_EmptyInput_ShouldReturnEmptyMap() {
        // Given
        Map<String, Object> variables = Collections.emptyMap();

        // When
        Map<String, VariableValueDto> result = mapper.map(variables);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    public void testCreateValue_ShouldReturnCorrectValue() {
        // Given
        Object value = "some value";

        // When
        VariableValueDto result = mapper.createValue(value);

        // Then
        assertEquals("some value", result.getValue());
    }
}