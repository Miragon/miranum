package io.miragon.miranum.connect.json.registry.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.registry.application.ports.in.ReadSchemaUseCase;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaUseCase;
import io.miragon.miranum.connect.json.registry.application.ports.out.LoadSchemaPort;
import io.miragon.miranum.connect.json.registry.application.ports.out.SaveSchemaPort;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import io.miragon.miranum.connect.json.registry.domain.SchemaNew;
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
    public Schema saveSchema(final String ref, final JsonNode jsonSchema) {
        final Optional<Schema> schemaOptional = this.loadSchemaPort.loadLatestSchema(ref);

        SchemaNew schemaNew = schemaOptional
                .map(schema -> new SchemaNew(schema, jsonSchema))
                .orElseGet(() -> new SchemaNew(ref, jsonSchema));

        return this.saveSchemaPort.saveSchema(schemaNew);
    }

    @Override
    public Schema loadLatestSchema(String ref) {
        return this.loadSchemaPort.loadLatestSchema(ref)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("No schema found for ref %s", ref)));
    }

    @Override
    public Schema loadVersionedSchema(String ref, Integer version) {
        return this.loadSchemaPort.loadVersionedSchema(ref, version)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("No schema found for ref %s and version %s", ref, version)));
    }
}
