package io.miragon.miranum.connect.json.registry.application.service;

import io.miragon.miranum.connect.json.registry.application.ports.in.ReadSchemaUseCase;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaInCommand;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaUseCase;
import io.miragon.miranum.connect.json.registry.application.ports.out.LoadSchemaPort;
import io.miragon.miranum.connect.json.registry.application.ports.out.SaveSchemaPort;
import io.miragon.miranum.connect.json.registry.application.service.exceptions.TagAlreadyExistsException;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import io.miragon.miranum.connect.shared.ObjectNotFoundException;
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

    @Override
    public Schema loadLatestSchema(String bundle, String ref) {
        return this.loadTaggedSchema(bundle, ref, "latest");
    }

    public Schema loadTaggedSchema(String bundle, String ref, String tag) {
        return this.loadSchemaPort.loadTaggedSchema(bundle, ref, tag)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("No schema found for bundle %s, ref %s and tag %s", bundle, ref, tag)));
    }

    private Schema mapToSchema(List<Schema> existingSchemasByRefAndBundle, SaveSchemaInCommand saveSchemaInCommand, String tag) {
        // create new Schema entry if no entry exists for the given tag
        if (existingSchemasByRefAndBundle.stream().map(Schema::getTag).noneMatch(tag::equals)) {
            return new Schema(
                    saveSchemaInCommand.getBundle(),
                    saveSchemaInCommand.getRef(),
                    tag,
                    saveSchemaInCommand.getJsonNode());
        }

        // overwrite "latest" Schema entry if tag is "latest"
        if (tag.equals("latest")) {
            Schema latestSchema = existingSchemasByRefAndBundle.stream()
                    .filter(schema -> schema.getTag().equals("latest"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No latest schema created yet.")); // ensured by condition above
            return new Schema(latestSchema, saveSchemaInCommand.getJsonNode());
        }

        throw new TagAlreadyExistsException(String.format("Tag %s already exists for bundle %s and ref %s.",
                tag,
                saveSchemaInCommand.getBundle(),
                saveSchemaInCommand.getRef()));
    }

}
