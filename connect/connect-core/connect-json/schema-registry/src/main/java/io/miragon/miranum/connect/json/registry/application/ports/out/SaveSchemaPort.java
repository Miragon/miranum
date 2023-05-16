package io.miragon.miranum.connect.json.registry.application.ports.out;

import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaCommand;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SaveSchemaPort {

    Schema saveSchema(@Valid SaveSchemaCommand saveSchemaCommand);

}
