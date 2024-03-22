package io.miranum.platform.connect.json.api;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonApi {

    JsonNode merge(Object source, Object update);

    JsonNode filter(JsonSchema schema, Object data);

    JsonSchema buildSchema(String schemaContent);


}
