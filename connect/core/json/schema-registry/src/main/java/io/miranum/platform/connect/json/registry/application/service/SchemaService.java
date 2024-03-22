package io.miranum.platform.connect.json.registry.application.service;

import io.miranum.platform.connect.json.registry.application.ports.in.ReadSchemaUseCase;
import io.miranum.platform.connect.json.registry.application.ports.in.SaveSchemaInCommand;
import io.miranum.platform.connect.json.registry.application.ports.in.SaveSchemaUseCase;
import io.miranum.platform.connect.json.registry.application.ports.out.LoadSchemaPort;
import io.miranum.platform.connect.json.registry.application.ports.out.SaveSchemaPort;
import io.miranum.platform.connect.json.registry.domain.Schema;
import io.miranum.platform.connect.shared.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchemaService implements SaveSchemaUseCase, ReadSchemaUseCase {

    private final SaveSchemaPort saveSchemaPort;
    private final LoadSchemaPort loadSchemaPort;

    @Override
    @Transactional
    public List<Schema> saveSchema(SaveSchemaInCommand saveSchemaInCommand) {
        final List<Schema> existingSchemas = this.loadSchemaPort
                .loadAllSchemaByRefAndBundle(saveSchemaInCommand.getBundle(), saveSchemaInCommand.getRef());

        List<Schema> schemasToCreate = saveSchemaInCommand.getTags().stream()
                .map(tag -> mapToSchema(existingSchemas, saveSchemaInCommand, tag.trim().toLowerCase()))
                .toList();

        return this.saveSchemaPort.saveAllSchemas(schemasToCreate);
    }

    public Schema loadSchema(String bundle, String ref, String tag) {
        return this.loadSchemaPort.loadTaggedSchema(bundle, ref, tag)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("No schema found for bundle %s, ref %s and tag %s", bundle, ref, tag)));
    }

    /**
     * Overwrites existing schema if tag already exist, otherwise creates a new schema.
     */
    private Schema mapToSchema(List<Schema> existingSchemasByRefAndBundle, SaveSchemaInCommand saveSchemaInCommand, String tag) {
        return existingSchemasByRefAndBundle.stream()
                .filter(schema -> schema.getTag().equals(tag))
                .findFirst()
                .map(existing -> new Schema(existing, saveSchemaInCommand.getJsonNode()))
                .orElse(new Schema(saveSchemaInCommand.getBundle(), saveSchemaInCommand.getRef(), tag, saveSchemaInCommand.getJsonNode()));
    }

}
