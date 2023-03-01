package io.miragon.miranum.connect.json.registry.application.ports.out;

import io.miragon.miranum.connect.json.registry.domain.Schema;

public interface LoadSchemaPort {

    Schema loadSchema(String ref);

}
