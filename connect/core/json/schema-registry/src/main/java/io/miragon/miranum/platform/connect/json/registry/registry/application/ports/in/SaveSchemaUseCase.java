package io.miragon.miranum.platform.connect.json.registry.registry.application.ports.in;

import io.miragon.miranum.platform.connect.json.registry.registry.domain.Schema;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface SaveSchemaUseCase {

    List<Schema> saveSchema(@Valid SaveSchemaInCommand saveSchemaInCommand);

}
