package io.miragon.miranum.platform.connect.json.registry.registry.adapter.out;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.platform.connect.json.registry.registry.domain.Schema;
import org.springframework.stereotype.Component;

@Component
public class SchemaEntityMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public Schema map(final SchemaEntity entity) {
        try {
            final JsonNode jsonNode = mapper.readTree(entity.getJsonNode());
            return new Schema(entity.getId(), entity.getBundle(), entity.getRef(), entity.getTag(), jsonNode);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public SchemaEntity map(final Schema entity) {
        return new SchemaEntity(
                entity.getId(),
                entity.getBundle(),
                entity.getRef(),
                entity.getTag(),
                entity.getJsonNode().toString());
    }

}
