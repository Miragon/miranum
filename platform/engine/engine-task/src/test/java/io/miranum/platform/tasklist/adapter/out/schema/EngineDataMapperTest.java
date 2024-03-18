package io.miranum.platform.tasklist.adapter.out.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EngineDataMapperTest {

    private final EngineDataMapper engineDataMapper = new EngineDataMapper(new ObjectMapper());

    @Test
    public void map_data() {
        val variables = generateVariables();

        val data = engineDataMapper.mapToData(variables);

        assertThat(data).isEqualTo(generateVariableResult());
    }

    private VariableMap generateVariables() {
        val variables = Variables.createVariables();
        variables.putValue("string", "test");
        variables.putValue("number", 1);
        variables.putValue("boolean", true);
        variables.putValue("list", Map.of("type", "json", "value", new JSONArray(List.of("a", "b", "c")).toString()));
        variables.putValue("object", Map.of("type", "json", "value", new JSONObject(Map.of("a", "1", "b", "2")).toString()));
        return variables;
    }

    private VariableMap generateVariableResult() {
        val variables = Variables.createVariables();
        variables.putValue("string", "test");
        variables.putValue("number", 1);
        variables.putValue("boolean", true);
        variables.putValue("list",
                new JSONArray(List.of("a", "b", "c")).toList()
        );
        variables.putValue("object",
                new JSONObject(Map.of("a", "1", "b", "2")).toMap()
        );
        return variables;
    }

}
