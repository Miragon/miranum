package io.miragon.miranum.connect.json.registry.application.service;

import io.miragon.miranum.connect.json.registry.application.ports.in.ReadSchemaUseCase;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaInCommand;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaUseCase;
import io.miragon.miranum.connect.json.registry.application.ports.out.LoadSchemaPort;
import io.miragon.miranum.connect.json.registry.application.ports.out.SaveSchemaOutCommand;
import io.miragon.miranum.connect.json.registry.application.ports.out.SaveSchemaPort;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import io.miragon.miranum.connect.shared.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchemaService implements SaveSchemaUseCase, ReadSchemaUseCase {

    private final SaveSchemaPort saveSchemaPort;
    private final LoadSchemaPort loadSchemaPort;

    @Override
    @Transactional
    public Schema saveSchema(SaveSchemaInCommand saveSchemaInCommand) {
        final Optional<Schema> schemaOptional = this.loadSchemaPort.loadLatestSchema(
                saveSchemaInCommand.getBundle(),
                saveSchemaInCommand.getRef());

        SaveSchemaOutCommand saveSchemaOutCommand = schemaOptional
                .map(schema -> new SaveSchemaOutCommand(schema, saveSchemaInCommand.getJsonNode()))
                .orElseGet(() -> new SaveSchemaOutCommand(saveSchemaInCommand.getBundle(), saveSchemaInCommand.getRef(), saveSchemaInCommand.getJsonNode()));

        return this.saveSchemaPort.saveSchema(saveSchemaOutCommand);
    }

    @Override
    public Schema loadLatestSchema(String bundle, String ref) {
        return this.loadSchemaPort.loadLatestSchema(bundle, ref)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("No schema found for bundle %s and ref %s", bundle, ref)));
    }

    @Override
    public Schema loadVersionedSchema(String bundle, String ref, Integer version) {
        return this.loadSchemaPort.loadVersionedSchema(bundle, ref, version)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("No schema found for bundle %s, ref %s and version %s", bundle, ref, version)));
    }
}
