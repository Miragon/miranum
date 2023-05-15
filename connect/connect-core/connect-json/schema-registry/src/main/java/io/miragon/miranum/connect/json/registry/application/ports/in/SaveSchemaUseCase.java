package io.miragon.miranum.connect.json.registry.application.ports.in;

import com.fasterxml.jackson.databind.JsonNode;

public interface SaveSchemaUseCase {

    void saveSchema(String ref, JsonNode jsonSchema);

}
