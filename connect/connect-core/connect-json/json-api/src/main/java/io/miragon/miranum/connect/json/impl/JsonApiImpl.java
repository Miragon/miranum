package io.miragon.miranum.connect.json.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.miragon.miranum.connect.json.api.JsonApi;
import io.miragon.miranum.connect.json.api.JsonSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;

public class JsonApiImpl implements JsonApi {

    private final ObjectMapper mapper = new ObjectMapper();


    @Value("${miranum.schema.registry.url}")
    private String schemaRegistryUrl;

    @Override
    public JsonSchema getSchema(final String schemaRef) {
        final RestTemplate restTemplate = new RestTemplate();
        final JsonNode schema = restTemplate.getForEntity(this.schemaRegistryUrl + schemaRef, JsonNode.class)
                .getBody();
        return JsonSchemaFactory.createJsonSchema(schema.toString());
    }

    @Override
    public JsonSchema createSchema(final String schemaContent) {
        return JsonSchemaFactory.createJsonSchema(schemaContent);
    }

    @Override
    public JsonNode merge(final Object source, final Object update) {

        final JsonNode updateNode = this.mapper.valueToTree(update);
        final JsonNode sourceNode = this.mapper.valueToTree(source);

        final Iterator<String> fieldNames = updateNode.fieldNames();
        while (fieldNames.hasNext()) {

            final String fieldName = fieldNames.next();
            final JsonNode jsonNode = sourceNode.get(fieldName);
            // if field exists and is an embedded object
            if (jsonNode != null && jsonNode.isObject()) {
                this.merge(jsonNode, updateNode.get(fieldName));
            } else {
                if (sourceNode instanceof ObjectNode) {
                    // Overwrite field
                    final JsonNode value = updateNode.get(fieldName);
                    ((ObjectNode) sourceNode).replace(fieldName, value);
                }
            }

        }

        return sourceNode;
    }

}
