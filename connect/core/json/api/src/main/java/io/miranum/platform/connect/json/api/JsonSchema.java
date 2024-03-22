package io.miranum.platform.connect.json.api;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonSchema {

    List<ValidationResult> validate(Object data);

    List<ValidationResult> validate(Object data, Object rootData);

    JsonNode getSchema();

}
