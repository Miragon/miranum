package io.miragon.miranum.connect.jsonschema.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.AbstractKeyword;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationContext;

public class ReadOnlyKeyword extends AbstractKeyword {

    public ReadOnlyKeyword() {
        super("readOnly");
    }

    @Override
    public ReadOnlyValidator newValidator(final String schemaPath, final JsonNode schemaNode, final JsonSchema parentSchema, final ValidationContext validationContext) {
        return new ReadOnlyValidator(schemaPath, schemaNode, parentSchema, validationContext);
    }

}
