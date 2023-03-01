package io.miragon.miranum.connect.json.registry.adapter.in;

import lombok.Data;

import java.util.Map;

@Data
public class SchemaDto {
    private Map<String, Object> schema;
}
