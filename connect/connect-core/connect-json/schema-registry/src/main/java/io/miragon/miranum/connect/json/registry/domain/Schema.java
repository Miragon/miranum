package io.miragon.miranum.connect.json.registry.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Schema {

    private final String id;
    private final String key;
    private final Integer version;
    private final JsonNode jsonSchema;

}
