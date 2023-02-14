package io.miragon.miranum.connect.json.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.miragon.miranum.connect.json.api.JsonApi;
import io.miragon.miranum.connect.json.api.JsonSchema;

import java.util.Iterator;

public class JsonApiImpl implements JsonApi {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonSchema getSchema(final String schemaKey) {
        return null;
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
