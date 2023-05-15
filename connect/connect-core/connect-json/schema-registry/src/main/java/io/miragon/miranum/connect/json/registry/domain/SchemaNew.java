package io.miragon.miranum.connect.json.registry.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class SchemaNew {

    private final String key;

    private final Integer version;

    private final JsonNode jsonSchema;

    public SchemaNew(final String key, final Integer version, final JsonNode jsonSchema) {
        this.key = key;
        this.version = version;
        this.jsonSchema = jsonSchema;
    }

    public SchemaNew(Schema existingSchema, final JsonNode jsonSchema) {
        this.key = existingSchema.getKey();
        this.version = existingSchema.getVersion() + 1;
        this.jsonSchema = jsonSchema;
    }

}
