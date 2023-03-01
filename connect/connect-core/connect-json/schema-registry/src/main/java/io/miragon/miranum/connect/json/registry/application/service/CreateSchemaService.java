package io.miragon.miranum.connect.json.registry.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.registry.application.ports.in.CreateSchemaUseCase;
import io.miragon.miranum.connect.json.registry.application.ports.out.SaveSchemaPort;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSchemaService implements CreateSchemaUseCase {

    private final SaveSchemaPort saveSchemaPort;

    @Override
    public void createSchema(final String ref, final JsonNode jsonSchema) {
        final Schema schema = new Schema(jsonSchema);
        this.saveSchemaPort.saveSchema(ref, schema);
    }

}
