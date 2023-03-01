package io.miragon.miranum.connect.json.registry.application.service;

import io.miragon.miranum.connect.json.registry.application.ports.in.SchemaQuery;
import io.miragon.miranum.connect.json.registry.application.ports.out.LoadSchemaPort;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchemaQueryService implements SchemaQuery {

    private final LoadSchemaPort loadSchemaPort;

    @Override
    public Schema findJsonSchemaByRef(final String ref) {
        return this.loadSchemaPort.loadSchema(ref);
    }
}
