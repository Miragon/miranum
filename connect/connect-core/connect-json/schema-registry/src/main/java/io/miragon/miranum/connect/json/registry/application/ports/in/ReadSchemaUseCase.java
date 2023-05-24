package io.miragon.miranum.connect.json.registry.application.ports.in;

import io.miragon.miranum.connect.json.registry.domain.Schema;

public interface ReadSchemaUseCase {

    Schema loadSchema(final String bundle, final String ref, final String tag);

}
