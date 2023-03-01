package io.miragon.miranum.connect.json.registry.application.ports.out;

import io.miragon.miranum.connect.json.registry.domain.Schema;

public interface SaveSchemaPort {

    void saveSchema(String key, Schema schema);

}
