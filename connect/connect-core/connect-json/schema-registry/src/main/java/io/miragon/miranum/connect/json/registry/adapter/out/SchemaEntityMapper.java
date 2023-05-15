package io.miragon.miranum.connect.json.registry.adapter.out;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import org.springframework.stereotype.Component;

@Component
public class SchemaEntityMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public Schema map(final SchemaEntity entity) {
        try {
            final JsonNode jsonNode = mapper.readTree(entity.getJsonSchema());
            return new Schema(entity.getId(), entity.getKey(), entity.getVersion(), jsonNode);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
