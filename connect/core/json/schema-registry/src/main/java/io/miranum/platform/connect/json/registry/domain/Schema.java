package io.miranum.platform.connect.json.registry.domain;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Schema {

    // Generated UUID.
    @NotNull
    @Size(min = 36, max = 36)
    private final String id;

    // All artefacts of one process are organized in a bundle.
    @NotNull
    @Size(min = 1, max = 255)
    private final String bundle;

    // The name of the schema.
    @NotNull
    @Size(min = 1, max = 255)
    private final String ref;

    // The tag of the schema.
    @NotNull
    @Size(min = 1, max = 255)
    private final String tag;

    // The schema content.
    @NotNull
    private final JsonNode jsonNode;

    public Schema(final String bundle, final String ref, final String tag, final JsonNode jsonNode) {
        this(UUID.randomUUID().toString(),
                bundle,
                ref,
                tag,
                jsonNode);
    }

    public Schema(final Schema latestSchema, final JsonNode jsonNode) {
        this.id = latestSchema.getId();
        this.bundle = latestSchema.getBundle();
        this.ref = latestSchema.getRef();
        this.tag = latestSchema.getTag();
        this.jsonNode = jsonNode;
    }
}
