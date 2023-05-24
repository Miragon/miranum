package io.miragon.miranum.connect.json.registry.adapter.in;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SchemaCreateDto {
    // The tag of the schema.
    @NotNull
    @Size(min = 1, max = 255)
    private final List<String> tags;

    // The schema content.
    @NotNull
    private final JsonNode jsonNode;
}
