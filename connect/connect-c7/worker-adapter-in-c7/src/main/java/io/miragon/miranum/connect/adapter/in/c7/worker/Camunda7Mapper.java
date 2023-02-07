package io.miragon.miranum.connect.adapter.in.c7.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ClassUtils;
import org.camunda.bpm.client.variable.impl.value.JsonValueImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Camunda7Mapper {

    public Object mapInput(final Class<?> inputType, final VariableMap engineData) {
        final Map<String, Object> data = this.fromEngineData(engineData);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(data, inputType);
    }

    public Map<String, Object> mapOutput(final Object output) {

        if (output == null) {
            return new HashMap<>();
        }

        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Object> result = mapper.convertValue(output, new TypeReference<Map<String, Object>>() {
        });
        return this.toEngineData(result);
    }

    //---------------------------------- helper methods ----------------------------------//

    private VariableMap toEngineData(final Map<String, Object> data) {
        final VariableMap variables = Variables.createVariables();
        data.keySet().forEach(key -> {
                    final Object value = data.get(key);
                    if (ClassUtils.isPrimitiveOrWrapper(value.getClass())) {
                        variables.putValue(key, value);
                    } else {
                        final JsonValueImpl json = new JsonValueImpl(value.toString());
                        variables.putValueTyped(key, json);
                    }
                }
        );
        return variables;
    }

    private Map<String, Object> fromEngineData(final VariableMap variables) {
        final Map<String, Object> data = new HashMap<>();
        variables.keySet().forEach(key -> {
            final TypedValue value = variables.getValueTyped(key);
            if (value.getType().getName().equals("json")) {
                try {
                    data.put(key, this.fromEngineData(value.getValue()));
                } catch (final JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                data.put(key, value.getValue());
            }
        });
        return data;
    }

    private Object fromEngineData(final Object value) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        if (value.toString().startsWith("[")) {
            return mapper.readValue(value.toString(), new TypeReference<List<?>>() {
            });
        }
        return mapper.readValue(value.toString(), new TypeReference<Map<String, Object>>() {
        });

    }

}
