package io.miranum.platform.engine.adapter.out.jsonschema;

import io.miranum.platform.engine.adapter.out.engine.MiranumEngineDataMapper;
import io.miranum.platform.engine.adapter.out.schema.SchemaClient;
import io.miranum.platform.engine.application.port.out.schema.JsonSchemaNotFoundException;
import io.miranum.platform.engine.application.port.out.schema.JsonSchemaPort;
import io.miranum.platform.engine.domain.jsonschema.JsonSchema;
import io.muenchendigital.digiwf.json.serialization.JsonSerializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RemoteJsonSchemaAdapter implements JsonSchemaPort {

    private final SchemaClient schemaClient;
    private final JsonSerializationService serializationService;
    private final MiranumEngineDataMapper engineDataMapper;

    @Override
    public JsonSchema getByRef(String ref) throws JsonSchemaNotFoundException {
        return JsonSchema.of(ref, schemaClient.getSchemaById("test", ref, "latest"));
    }

    @Override
    public Map<String, Object> filterVariables(final Map<String, Object> data, final String ref) {
        final JsonSchema schema = this.getByRef(ref);
        return this.engineDataMapper.mapToData(this.serializationService.deserializeData(schema.asMap(), data));
    }

}
