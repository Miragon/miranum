package io.miragon.miranum.connect.json.registry.application.ports.out;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class SaveSchemaOutCommand {

    @NotNull
    private final String bundle;

    @NotNull
    private final String ref;

    @Positive
    private final Integer version;

    @NotNull
    private final JsonNode jsonNode;

    public SaveSchemaOutCommand(final String bundle, final String ref, final JsonNode jsonNode) {
        this.bundle = bundle;
        this.ref = ref;
        this.version = 1;
        this.jsonNode = jsonNode;
    }

    public SaveSchemaOutCommand(Schema existingSchema, final JsonNode jsonNode) {
        this.bundle = existingSchema.getBundle();
        this.ref = existingSchema.getRef();
        this.version = existingSchema.getVersion() + 1;
        this.jsonNode = jsonNode;
    }

}
