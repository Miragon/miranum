package io.miragon.miranum.platform.connect.json.registry.registry.application.ports.out;

import io.miragon.miranum.platform.connect.json.registry.registry.domain.Schema;

import java.util.List;
import java.util.Optional;

public interface LoadSchemaPort {

    List<Schema> loadAllSchemaByRefAndBundle(final String bundle, final String ref);

    Optional<Schema> loadTaggedSchema(final String bundle, final String ref, final String tag);

}
