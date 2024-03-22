package io.miranum.platform.connect.json.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;
import io.miranum.platform.connect.json.api.JsonSchema;
import io.miranum.platform.connect.json.api.ValidationResult;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class JsonSchemaImpl implements JsonSchema {

    private final com.networknt.schema.JsonSchema schema;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<ValidationResult> validate(final Object data) {
        final JsonNode node = this.mapper.valueToTree(data);
        return this.schema.validate(node)
                .stream()
                .map(this::map).toList();
    }

    @Override
    public List<ValidationResult> validate(final Object data, final Object rootData) {
        final JsonNode node = this.mapper.valueToTree(data);
        final JsonNode rootNode = this.mapper.valueToTree(rootData);
        return this.schema.validate(node, rootNode, "$")
                .stream()
                .map(this::map).toList();
    }

    @Override
    public JsonNode getSchema() {
        return this.schema.getSchemaNode();
    }

    private ValidationResult map(final ValidationMessage obj) {
        return new ValidationResult(
                obj.getType(),
                obj.getCode(),
                obj.getPath(),
                obj.getSchemaPath(),
                obj.getArguments(),
                obj.getDetails(),
                obj.getMessage()
        );
    }
}
