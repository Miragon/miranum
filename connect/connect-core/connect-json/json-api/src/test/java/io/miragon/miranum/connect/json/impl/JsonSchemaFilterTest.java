package io.miragon.miranum.connect.json.impl;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.api.JsonApi;
import io.miragon.miranum.connect.json.api.JsonSchema;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static io.miragon.miranum.connect.json.utils.JsonSchemaTestUtils.getSchemaString;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonSchemaFilterTest {

    private final JsonApi jsonApi = new JsonApiImpl();

    @Test
    public void filter_additional_property() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.of(
                "stringProp1", "fsdafsda",
                "numberProp1", 1,
                "stringProp5", 1
        );

        final JsonNode node = this.jsonApi.filter(schema, actualData);

        assertNull(node.get("stringProp5"));
    }

    @Test
    public void test_user_schema() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/additional-data-schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.of(
                "addressOrUser", Map.of("city", "Augsburg",
                        "name", "Dom",
                        "street_address", "Augstreet 1",
                        "state", "Bavaria",
                        "additionalData", "additionalData",
                        "mail", "test@mail.de")

        );

        final JsonNode node = this.jsonApi.filter(schema, actualData);

        assertNull(node.get("additionalData"));
    }

    protected JsonSchema getSchemaFronString(final String schemaContent) {
        return JsonSchemaFactory.createJsonSchema(schemaContent);
    }

}
