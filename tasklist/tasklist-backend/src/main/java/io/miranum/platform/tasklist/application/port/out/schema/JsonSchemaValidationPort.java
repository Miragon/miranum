package io.miranum.platform.tasklist.application.port.out.schema;

import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.domain.JsonSchema;

import java.util.Map;

/**
 * Port to access JSON validation services.
 */
public interface JsonSchemaValidationPort {

    /**
     * Validates variables and returns the serialized version of them.
     *
     * @param schema    schema to validate the input
     * @param task      user task
     * @param variables variables to validate and serialize.
     * @return serialized and validated version.
     */
    Map<String, Object> validateAndSerialize(JsonSchema schema, Task task, Map<String, Object> variables);
}
