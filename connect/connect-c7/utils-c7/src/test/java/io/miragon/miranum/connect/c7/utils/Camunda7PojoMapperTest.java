package io.miragon.miranum.connect.c7.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Camunda7PojoMapperTest {

    private final Camunda7PojoMapper mapper = new Camunda7PojoMapper();

    @Test
    public void testMap_ShouldReturnMappedVariables() throws JsonProcessingException {
        final String KEY_1 = "var1";
        final String KEY_2 = "var2";
        final String VALUE_1 = "value2";
        final Map<String, String> VALUE_2 = Map.ofEntries(entry("key", "value"));

        // Given
        Map<String, Object> variables = Map.ofEntries(
                entry(KEY_1, VALUE_1),
                entry(KEY_2, VALUE_2)
        );

        // When
        VariableMap result = mapper.mapToEngineData(variables);

        // Then
        assertEquals(2, result.size());
        assertEquals(VALUE_1, result.get(KEY_1));
        assertEquals("{\"key\":\"value\"}", result.get(KEY_2).toString());
    }

    @Test
    public void testMap_EmptyInput_ShouldReturnEmptyMap() throws JsonProcessingException {
        // Given
        Map<String, Object> variables = Collections.emptyMap();

        // When
        VariableMap result = mapper.mapToEngineData(variables);

        // Then
        assertTrue(result.isEmpty());
    }

}
