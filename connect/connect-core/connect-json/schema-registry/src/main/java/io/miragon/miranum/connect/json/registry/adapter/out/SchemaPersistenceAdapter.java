package io.miragon.miranum.connect.json.registry.adapter.out;

import io.miragon.miranum.connect.json.registry.application.ports.out.LoadSchemaPort;
import io.miragon.miranum.connect.json.registry.application.ports.out.SaveSchemaPort;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import io.miragon.miranum.connect.json.registry.domain.SchemaNew;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SchemaPersistenceAdapter implements SaveSchemaPort, LoadSchemaPort {

    private final SchemaRepository schemaRepository;
    private final SchemaEntityMapper schemaEntityMapper;

    @Override
    public void saveSchema(final SchemaNew schemaNew) {
        final SchemaEntity entity = new SchemaEntity(null, schemaNew.getKey(), schemaNew.getVersion(), schemaNew.getJsonSchema().toString());
        this.schemaRepository.save(entity);
    }

    @Override
    public Optional<Schema> loadLatestSchema(final String ref) {
        final Optional<SchemaEntity> entity = this.schemaRepository.findLatestByKey(ref);
        return Optional.of(entity
                .map(this.schemaEntityMapper::map))
                .orElse(Optional.empty());
    }

    @Override
    public Optional<Schema> loadVersionedSchema(String ref, Integer version) {
        final Optional<SchemaEntity> entity = this.schemaRepository.findByKeyAndVersion(ref, version);
        return Optional.of(entity.map(this.schemaEntityMapper::map))
                    .orElse(Optional.empty());
    }
}
