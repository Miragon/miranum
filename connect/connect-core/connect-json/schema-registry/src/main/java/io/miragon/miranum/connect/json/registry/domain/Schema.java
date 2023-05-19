package io.miragon.miranum.connect.json.registry.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Schema {

    // Generated UUID
    private final String id;

    // All artefacts of one process are organized in a bundle.
    private final String bundle;

    // The name of the schema.
    private final String ref;

    // The version of the schema.
    private final Integer version;

    // The schema content.
    private final JsonNode jsonNode;

}
