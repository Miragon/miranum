package io.miragon.miranum.connect.json.impl;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.networknt.schema.ValidatorTypeCode;
import io.miragon.miranum.connect.json.api.JsonApi;
import io.miragon.miranum.connect.json.api.JsonSchema;
import io.miragon.miranum.connect.json.api.ValidationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

public class JsonApiImpl implements JsonApi {

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${miranum.schema.registry.url:}")
    private String schemaRegistryUrl;

    @Override
    public JsonSchema getSchema(final String schemaRef) {
        final RestTemplate restTemplate = new RestTemplate();
        final JsonNode schema = restTemplate.getForEntity(this.schemaRegistryUrl + schemaRef, JsonNode.class)
                .getBody();
        return JsonSchemaFactory.createJsonSchema(schema.toString());
    }

    @Override
    public JsonSchema buildSchema(final String schemaContent) {
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

    @Override
    public JsonNode filter(final JsonSchema schema, final Object data) {
        final JsonNode jsonData = this.mapper.valueToTree(data);

        final List<ValidationResult> result = schema.validate(jsonData);

        final List<ValidationResult> additionalProperties = result.stream()
                .filter(validationResult -> validationResult.getCode().equals(ValidatorTypeCode.ADDITIONAL_PROPERTIES.getErrorCode()))
                .toList();

        additionalProperties.forEach(validationResult -> {
            final String path = validationResult.getPath();
            String pointerString = path.replaceAll("\\$", "");
            pointerString = pointerString.replaceAll("\\.", "/");
            pointerString = pointerString.replaceAll("\\[", "/");
            pointerString = pointerString.replaceAll("]", "/");
            pointerString = pointerString.endsWith("/") ? pointerString.substring(0, pointerString.length() - 1) : pointerString;
            final JsonPointer pointer = JsonPointer.compile(pointerString);
            final JsonNode node = jsonData.at(pointer);
            if (node instanceof ObjectNode objectNode) {
                objectNode.remove(validationResult.getArguments()[0]);
            }
        });

        return jsonData;
    }

}
