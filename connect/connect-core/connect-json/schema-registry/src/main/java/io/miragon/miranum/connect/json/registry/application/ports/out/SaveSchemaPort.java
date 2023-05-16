package io.miragon.miranum.connect.json.registry.application.ports.out;

import io.miragon.miranum.connect.json.registry.domain.Schema;
import io.miragon.miranum.connect.json.registry.domain.SchemaNew;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SaveSchemaPort {

    Schema saveSchema(@Valid SchemaNew schemaNew);

}
