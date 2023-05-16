package io.miragon.miranum.connect.json.registry.application.ports.in;

import io.miragon.miranum.connect.json.registry.domain.Schema;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SaveSchemaUseCase {

    Schema saveSchema(@Valid SaveSchemaInCommand saveSchemaInCommand);

}
