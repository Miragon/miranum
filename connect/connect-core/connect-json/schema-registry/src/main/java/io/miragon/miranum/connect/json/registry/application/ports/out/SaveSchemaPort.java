package io.miragon.miranum.connect.json.registry.application.ports.out;

import io.miragon.miranum.connect.json.registry.domain.SchemaNew;

public interface SaveSchemaPort {

    void saveSchema(SchemaNew schemaNew);

}
