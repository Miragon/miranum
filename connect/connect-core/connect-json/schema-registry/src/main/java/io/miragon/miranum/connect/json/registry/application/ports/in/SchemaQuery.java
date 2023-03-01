package io.miragon.miranum.connect.json.registry.application.ports.in;

import io.miragon.miranum.connect.json.registry.domain.Schema;

public interface SchemaQuery {

    Schema findJsonSchemaByRef(String ref);

}
