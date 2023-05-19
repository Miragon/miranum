package io.miragon.miranum.connect.schema.api;

import com.fasterxml.jackson.databind.JsonNode;

public interface SchemaApi {

    JsonNode loadLatestSchema(final String bundle, final String ref);

    JsonNode loadVersionedSchema(final String bundle, final String ref, final Integer version);

}
