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
    public Schema saveSchema(final SchemaNew schemaNew) {
        final SchemaEntity entity = new SchemaEntity(null, schemaNew.getRef(), schemaNew.getVersion(), schemaNew.getJsonSchema().toString());
        final SchemaEntity savedEntity = this.schemaRepository.save(entity);
        return this.schemaEntityMapper.map(savedEntity);
    }

    @Override
    public Optional<Schema> loadLatestSchema(final String key) {
        final Optional<SchemaEntity> entity = this.schemaRepository.findLatestByRef(key);
        return entity.map(this.schemaEntityMapper::map);
    }

    @Override
    public Optional<Schema> loadVersionedSchema(String ref, Integer version) {
        final Optional<SchemaEntity> entity = this.schemaRepository.findByRefAndVersion(ref, version);
        return entity.map(this.schemaEntityMapper::map);
    }
}
