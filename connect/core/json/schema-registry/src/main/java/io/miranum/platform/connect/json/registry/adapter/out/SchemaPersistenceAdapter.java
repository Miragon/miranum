package io.miranum.platform.connect.json.registry.adapter.out;

import io.miranum.platform.connect.json.registry.application.ports.out.LoadSchemaPort;
import io.miranum.platform.connect.json.registry.application.ports.out.SaveSchemaPort;
import io.miranum.platform.connect.json.registry.domain.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SchemaPersistenceAdapter implements SaveSchemaPort, LoadSchemaPort {

    private final SchemaRepository schemaRepository;
    private final SchemaEntityMapper schemaEntityMapper;

    @Override
    public List<Schema> loadAllSchemaByRefAndBundle(String bundle, String ref) {
        return schemaRepository.findAllByBundleAndRef(bundle, ref).stream()
                .map(schemaEntityMapper::map)
                .toList();
    }

    @Override
    public Optional<Schema> loadTaggedSchema(final String bundle, final String ref, final String tag) {
        final Optional<SchemaEntity> entity = this.schemaRepository.findByBundleAndRefAndTag(bundle, ref, tag);
        return entity.map(this.schemaEntityMapper::map);
    }

    @Override
    public List<Schema> saveAllSchemas(List<@Valid Schema> schemasToSave) {
        return schemaRepository.saveAll(schemasToSave.stream()
                        .map(schemaEntityMapper::map)
                        .toList())
                .stream()
                .map(schemaEntityMapper::map)
                .toList();
    }
}
