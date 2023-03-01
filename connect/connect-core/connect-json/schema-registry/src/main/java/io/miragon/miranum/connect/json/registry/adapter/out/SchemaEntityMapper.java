package io.miragon.miranum.connect.json.registry.adapter.out;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import org.springframework.stereotype.Component;

@Component
public class SchemaEntityMapper {

    public Schema map(final SchemaEntity jsonSchema) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode jsonNode = mapper.readTree(jsonSchema.getSchema());
        return new Schema(jsonNode);
    }

}
