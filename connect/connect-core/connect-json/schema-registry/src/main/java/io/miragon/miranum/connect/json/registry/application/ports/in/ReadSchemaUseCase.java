package io.miragon.miranum.connect.json.registry.application.ports.in;

import io.miragon.miranum.connect.json.registry.domain.Schema;

public interface ReadSchemaUseCase {

    Schema loadLatestSchema(String ref);

    Schema loadVersionedSchema(String ref, Integer version);

}
