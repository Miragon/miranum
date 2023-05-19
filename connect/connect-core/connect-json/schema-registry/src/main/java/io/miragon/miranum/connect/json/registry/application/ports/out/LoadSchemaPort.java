package io.miragon.miranum.connect.json.registry.application.ports.out;

import io.miragon.miranum.connect.json.registry.domain.Schema;

import java.util.Optional;

public interface LoadSchemaPort {

    Optional<Schema> loadLatestSchema(final String bundle, final String ref);

    Optional<Schema> loadVersionedSchema(final String bundle, final String ref, final Integer version);

}
