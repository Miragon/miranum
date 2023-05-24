package io.miranum.platform.engine.processdefinition.domain.service;

import org.camunda.bpm.engine.variable.Variables;
import org.camunda.spin.impl.json.jackson.JacksonJsonNode;
import org.camunda.spin.plugin.variable.SpinValues;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper
public interface MiranumEngineDataMapper {

    default Map<String, Object> mapObjectsToVariables(final Map<String, Object> data) {
        final JSONObject jsonData = new JSONObject(data);
        final Map<String, Object> variables = Variables.createVariables();
        jsonData.keySet().forEach(key -> {
                    final Object value = jsonData.get(key);
                    if (value instanceof JSONObject || value instanceof JSONArray) {
                        variables.put(key, this.mapJsonToVariables(value));
                    } else {
                        variables.put(key, value);
                    }
                }
        );
        return variables;
    }

    default Map<String, Object> mapToData(final Map<String, Object> variables) {
        final Map<String, Object> data = Variables.createVariables();
        variables.forEach((key, value) -> {
            if (value instanceof JacksonJsonNode) {
                data.put(key, this.mapToData((JacksonJsonNode) value));
            } else {
                data.put(key, value);
            }
        });
        return data;
    }

    default Object mapJsonToVariables(final Object object) {
        return SpinValues.jsonValue(object.toString()).create();
    }

    //TODO Is there a more elegant way?
    default Object mapToData(final JacksonJsonNode object) {
        if (object.isArray()) {
            return new JSONArray(object.toString()).toList();
        }
        return new JSONObject(object.toString()).toMap();
    }


}
