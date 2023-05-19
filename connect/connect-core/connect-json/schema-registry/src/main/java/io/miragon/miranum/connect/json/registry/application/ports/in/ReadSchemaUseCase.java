package io.miragon.miranum.connect.json.registry.application.ports.in;

import io.miragon.miranum.connect.json.registry.domain.Schema;

public interface ReadSchemaUseCase {

    Schema loadLatestSchema(final String bundle, final String ref);

    Schema loadVersionedSchema(final String bundle, final String ref, final Integer version);

}
