package io.miranum.platform.connect.json.registry.adapter.in;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemaDto {
    // All artefacts of one process are organized in a bundle.
    @NotNull
    @Size(min = 1, max = 255)
    private String bundle;

    // The name of the schema.
    @NotNull
    @Size(min = 1, max = 255)
    private String ref;

    // The tag of the schema.
    @NotNull
    @Size(min = 1, max = 255)
    private String tag;

    // The schema content.
    @NotNull
    private JsonNode jsonNode;
}
