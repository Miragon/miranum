package io.miranum.platform.tasklist.application.port.out.schema;


import io.miranum.platform.tasklist.domain.JsonSchema;

/**
 * Port to access the schema.
 */
public interface JsonSchemaPort {

  /**
   * Load schema by id.
   * @param schemaId id of the schema.
   * @return schema
   * @throws JsonSchemaNotFoundException if no schema is available or access is restricted.
   */
  JsonSchema getSchemaById(String schemaId) throws JsonSchemaNotFoundException;

}
