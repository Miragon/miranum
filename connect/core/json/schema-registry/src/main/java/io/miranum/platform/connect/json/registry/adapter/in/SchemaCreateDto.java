package io.miranum.platform.connect.json.registry.adapter.in;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemaCreateDto {
    // The tag of the schema.
    @NotNull
    @Size(min = 1, max = 255)
    private List<String> tags;

    // The schema content.
    @NotNull
    private JsonNode jsonNode;
}
