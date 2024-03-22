package io.miranum.platform.connect.json.registry.application.ports.in;

import io.miranum.platform.connect.json.registry.domain.Schema;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface SaveSchemaUseCase {

    List<Schema> saveSchema(@Valid SaveSchemaInCommand saveSchemaInCommand);

}
