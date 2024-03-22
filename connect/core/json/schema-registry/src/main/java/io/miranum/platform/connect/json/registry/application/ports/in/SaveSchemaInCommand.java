package io.miranum.platform.connect.json.registry.application.ports.in;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SaveSchemaInCommand {

    @NotNull
    private final String bundle;

    @NotNull
    private final String ref;

    @NotNull
    private final List<String> tags;

    @NotNull
    private final JsonNode jsonNode;

}
