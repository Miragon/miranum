package io.miragon.miranum.connect.json.registry.application.ports.in;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveSchemaInCommand {

    @NotNull
    private final String bundle;

    @NotNull
    private final String ref;

    @NotNull
    private final JsonNode jsonNode;

}
