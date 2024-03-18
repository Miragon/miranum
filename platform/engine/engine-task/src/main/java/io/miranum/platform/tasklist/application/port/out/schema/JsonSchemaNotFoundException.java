package io.miranum.platform.tasklist.application.port.out.schema;


/**
 * Thrown if JSON Schema is not found or is inaccessible.
 */
public class JsonSchemaNotFoundException extends RuntimeException {
  public JsonSchemaNotFoundException(String schemaId) {
    super("JSON Schema with id '" + schemaId + "' could not be found.");
  }
}
