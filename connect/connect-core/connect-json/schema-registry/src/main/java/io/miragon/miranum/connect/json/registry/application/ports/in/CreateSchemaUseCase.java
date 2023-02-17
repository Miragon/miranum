package io.miragon.miranum.connect.json.registry.application.ports.in;

import com.fasterxml.jackson.databind.JsonNode;

public interface CreateSchemaUseCase {

    void createSchema(String ref, JsonNode jsonSchema);

}
