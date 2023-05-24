package io.miranum.platform.engine.application.port.out.schema;


import io.miranum.platform.engine.domain.jsonschema.JsonSchema;

import java.util.Map;

/**
 * Port to access the schema.
 */
public interface JsonSchemaPort {

    /**
     * Load schema by id.
     *
     * @param ref id of the schema.
     * @return schema
     * @throws JsonSchemaNotFoundException if no schema is available or access is restricted.
     */
    JsonSchema getByRef(String ref) throws JsonSchemaNotFoundException;

    /**
     * Filter variables by schema.
     *
     * @param ref of the schema.
     */
    Map<String, Object> filterVariables(final Map<String, Object> data, final String ref);

}
