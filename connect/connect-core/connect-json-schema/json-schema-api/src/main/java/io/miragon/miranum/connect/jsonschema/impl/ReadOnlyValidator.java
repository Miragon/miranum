package io.miragon.miranum.connect.jsonschema.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class ReadOnlyValidator extends BaseJsonValidator implements JsonValidator {
    private static final Logger logger = LoggerFactory.getLogger(RequiredValidator.class);

    public ReadOnlyValidator(final String schemaPath, final JsonNode schemaNode, final JsonSchema parentSchema, final ValidationContext validationContext) {
        super(schemaPath, schemaNode, parentSchema, ValidatorTypeCode.READ_ONLY, validationContext);


        this.parseErrorCode(this.getValidatorType().getErrorCodeKey());
    }

    public Set<ValidationMessage> validate(final JsonNode node, final JsonNode rootNode, final String at) {
        this.debug(logger, node, rootNode, at);

        final Set<ValidationMessage> errors = new LinkedHashSet<>();

        final JsonNode originalNode = this.getNode(at, rootNode);

        final boolean theSame = originalNode != null && originalNode.equals(node);
        if (!theSame) {
            errors.add(this.buildValidationMessage(at));
        }

        return Collections.unmodifiableSet(errors);
    }

    private JsonNode getNode(final String datapath, JsonNode data) {
        final String path = this.getSubString(datapath, "$.");

        final String[] parts = path.split("\\.");
        JsonNode result = null;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].contains("[")) {
                final int idx1 = parts[i].indexOf("[");
                final int idx2 = parts[i].indexOf("]");
                final String key = parts[i].substring(0, idx1).trim();
                final int idx = Integer.parseInt(parts[i].substring(idx1 + 1, idx2).trim());
                result = data.get(key).get(idx);
            } else {
                result = data.get(parts[i]);
            }
            if (result == null) {
                break;
            }
            data = result;
        }
        return result;
    }

    private String getSubString(final String datapath, final String keyword) {
        String path = datapath;
        if (path.startsWith(keyword)) {
            path = path.substring(2);
        }
        return path;
    }

}
