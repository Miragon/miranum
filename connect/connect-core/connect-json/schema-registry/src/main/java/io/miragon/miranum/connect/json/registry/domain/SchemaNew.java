package io.miragon.miranum.connect.json.registry.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SchemaNew {

    @NotNull
    private final String ref;

    @Positive
    private final Integer version;

    @NotNull
    private final JsonNode jsonSchema;

    public SchemaNew(final String ref, final JsonNode jsonSchema) {
        this.ref = ref;
        this.version = 1;
        this.jsonSchema = jsonSchema;
    }

    public SchemaNew(Schema existingSchema, final JsonNode jsonSchema) {
        this.ref = existingSchema.getRef();
        this.version = existingSchema.getVersion() + 1;
        this.jsonSchema = jsonSchema;
    }

}
