package io.miragon.miranum.connect.json.registry.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.miragon.miranum.connect.json.registry.application.ports.out.LoadSchemaPort;
import io.miragon.miranum.connect.json.registry.application.ports.out.SaveSchemaPort;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SchemaPersistanceAdapter implements SaveSchemaPort, LoadSchemaPort {

    private final SchemaRepository schemaRepository;
    private final SchemaEntityMapper schemaEntityMapper;

    @Override
    public void saveSchema(final String key, final Schema schema) {
        final SchemaEntity entity = new SchemaEntity(key, schema.getSchema().toString());
        this.schemaRepository.save(entity);
    }

    @Override
    public Schema loadSchema(final String ref) {
        final SchemaEntity entity = this.schemaRepository.findById(ref).orElseThrow();
        try {
            return this.schemaEntityMapper.map(entity);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
