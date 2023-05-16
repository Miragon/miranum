package io.miragon.miranum.connect.json.registry.application.ports.in;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SaveSchemaCommand {

    @NotNull
    private final String ref;

    @Positive
    private final Integer version;

    @NotNull
    private final JsonNode jsonNode;

    public SaveSchemaCommand(final String ref, final JsonNode jsonNode) {
        this.ref = ref;
        this.version = 1;
        this.jsonNode = jsonNode;
    }

    public SaveSchemaCommand(Schema existingSchema, final JsonNode jsonNode) {
        this.ref = existingSchema.getRef();
        this.version = existingSchema.getVersion() + 1;
        this.jsonNode = jsonNode;
    }

}
