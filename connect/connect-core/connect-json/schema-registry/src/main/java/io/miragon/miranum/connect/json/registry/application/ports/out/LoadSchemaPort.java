package io.miragon.miranum.connect.json.registry.application.ports.out;

import io.miragon.miranum.connect.json.registry.domain.Schema;

import java.util.Optional;

public interface LoadSchemaPort {

    Optional<Schema> loadLatestSchema(String ref);

    Optional<Schema> loadVersionedSchema(String ref, Integer version);

}
