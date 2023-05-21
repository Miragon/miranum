package io.miranum.platform.tasklist.domain;

import io.muenchendigital.digiwf.json.factory.JsonSchemaFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Map;

@RequiredArgsConstructor
@Data
public class JsonSchema {
    private final String id;
    private final String schema;

    public static JsonSchema of(String id, Map<String, Object> map) {
        val json = JsonSchemaFactory.gson().toJson(map);
        return new JsonSchema(id, json);
    }

    /**
     * Returns schema as map.
     *
     * @return JSON schema itself is a JSON document, here represented as a map.
     */
    public Map<String, Object> asMap() {
        return JsonSchemaFactory.gson().fromJson(this.schema, JsonSchemaFactory.mapType());
    }
}

