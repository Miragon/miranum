package io.miragon.miranum.connect.json.registry.adapter.out;

import io.miragon.miranum.connect.json.registry.application.ports.out.SaveSchemaOutCommand;
import io.miragon.miranum.connect.json.registry.application.ports.out.LoadSchemaPort;
import io.miragon.miranum.connect.json.registry.application.ports.out.SaveSchemaPort;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SchemaPersistenceAdapter implements SaveSchemaPort, LoadSchemaPort {

    private final SchemaRepository schemaRepository;
    private final SchemaEntityMapper schemaEntityMapper;

    @Override
    public Schema saveSchema(final SaveSchemaOutCommand saveSchemaOutCommand) {
        final SchemaEntity entity = new SchemaEntity(
                null, // set by JPA
                saveSchemaOutCommand.getBundle(),
                saveSchemaOutCommand.getRef(),
                saveSchemaOutCommand.getVersion(),
                saveSchemaOutCommand.getJsonNode().toString());
        final SchemaEntity savedEntity = this.schemaRepository.save(entity);
        return schemaEntityMapper.map(savedEntity);
    }

    @Override
    public Optional<Schema> loadLatestSchema(final String bundle, final String ref) {
        final List<SchemaEntity> entities = this.schemaRepository.findAllByBundleAndRef(bundle, ref);
        final Optional<SchemaEntity> entity = entities.stream()
                .max(Comparator.comparingInt(SchemaEntity::getVersion));
        return entity.map(this.schemaEntityMapper::map);
    }

    @Override
    public Optional<Schema> loadVersionedSchema(final String bundle, final String ref, final Integer version) {
        final Optional<SchemaEntity> entity = this.schemaRepository.findByBundleAndRefAndVersion(bundle, ref, version);
        return entity.map(this.schemaEntityMapper::map);
    }
}
