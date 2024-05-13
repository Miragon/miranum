package io.miragon.miranum.platform.connect.json.registry.application.ports.out;

import io.miragon.miranum.platform.connect.json.registry.domain.Schema;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface SaveSchemaPort {

    List<Schema> saveAllSchemas(List<@Valid Schema> schemasToSave);

}
