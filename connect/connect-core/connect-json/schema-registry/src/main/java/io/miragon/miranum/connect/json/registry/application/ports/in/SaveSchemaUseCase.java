package io.miragon.miranum.connect.json.registry.application.ports.in;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.registry.domain.Schema;

public interface SaveSchemaUseCase {

    Schema saveSchema(String ref, JsonNode jsonSchema);

}
