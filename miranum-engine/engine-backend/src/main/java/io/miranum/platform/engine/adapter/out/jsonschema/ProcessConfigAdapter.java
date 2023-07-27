package io.miranum.platform.engine.adapter.out.jsonschema;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.miranum.platform.engine.adapter.out.schema.SchemaClient;
import io.miranum.platform.engine.application.port.out.process.ProcessConfigPort;
import io.miranum.platform.engine.application.port.out.schema.JsonSchemaNotFoundException;
import io.miranum.platform.engine.domain.process.ProcessConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProcessConfigAdapter implements ProcessConfigPort {

    private final SchemaClient schemaClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ProcessConfig getByRef(String ref) throws JsonSchemaNotFoundException {
        final Map<String, Object> schema = schemaClient.getSchemaById("test", ref, "latest");
        return objectMapper.convertValue(schema, ProcessConfig.class);
    }
}
