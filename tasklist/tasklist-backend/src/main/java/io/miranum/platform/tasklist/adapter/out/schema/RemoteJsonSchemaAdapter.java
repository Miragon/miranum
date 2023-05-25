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
    public JsonSchema getByRef(String ref) throws JsonSchemaNotFoundException {
        return JsonSchema.of(ref, jsonSchemaClient.getSchemaById("test", ref, "latest"));
    }
}
