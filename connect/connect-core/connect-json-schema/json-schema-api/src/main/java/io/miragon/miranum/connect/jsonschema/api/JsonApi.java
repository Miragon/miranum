package io.miragon.miranum.connect.jsonschema.api;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonApi {

    JsonNode merge(Object source, Object update);

    void getSchema(String schemaKey);


}
