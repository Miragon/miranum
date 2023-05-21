package io.miranum.platform.tasklist.adapter.out.schema;

import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaNotFoundException;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaPort;
import io.miranum.platform.tasklist.domain.JsonSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoteJsonSchemaAdapter implements JsonSchemaPort {

    private final JsonSchemaClient jsonSchemaClient;

    @Override
    public JsonSchema getSchemaById(String schemaId) throws JsonSchemaNotFoundException {
        return JsonSchema.of(schemaId, jsonSchemaClient.getSchemaById(schemaId));
    }
}
