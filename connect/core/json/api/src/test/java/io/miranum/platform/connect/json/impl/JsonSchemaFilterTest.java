package io.miranum.platform.connect.json.impl;

import com.fasterxml.jackson.databind.JsonNode;
import io.miranum.platform.connect.json.utils.JsonSchemaTestUtils;
import io.miranum.platform.connect.json.api.JsonApi;
import io.miranum.platform.connect.json.api.JsonSchema;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonSchemaFilterTest {

    private final JsonApi jsonApi = new JsonApiImpl();

    @Test
    public void filter_additional_property() throws IOException, URISyntaxException {
        final String rawSchema = JsonSchemaTestUtils.getSchemaString("/schema/schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.ofEntries(
                entry("stringProp1", "fsdafsda"),
                entry("numberProp1", 1),
                entry("stringProp5", 1)
        );

        final JsonNode node = this.jsonApi.filter(schema, actualData);

        assertNull(node.get("stringProp5"));
    }

    @Test
    public void test_user_schema() throws IOException, URISyntaxException {
        final String rawSchema = JsonSchemaTestUtils.getSchemaString("/schema/additional-data-schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.ofEntries(
                entry("addressOrUser", Map.ofEntries(
                                entry("city", "Augsburg"),
                                entry("name", "Dom"),
                                entry("street_address", "Augstreet 1"),
                                entry("state", "Bavaria"),
                                entry("additionalData", "additionalData"),
                                entry("mail", "test@mail.de")
                        )
                )

        );

        final JsonNode node = this.jsonApi.filter(schema, actualData);

        assertNull(node.get("additionalData"));
    }

    @Test
    public void object_schema_list_invalid() throws IOException, URISyntaxException {
        final String rawSchema = JsonSchemaTestUtils.getSchemaString("/schema/object-list-schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.ofEntries(
                entry("person", List.of(
                        Map.ofEntries(
                                entry("vorname", "Dom"),
                                entry("nachname", "Hrn")),
                        Map.ofEntries(
                                entry("vorname", "Dom"),
                                entry("street", "InvalidStreet"),
                                entry("nachname", "Hrn"))
                ))
        );

        final JsonNode node = this.jsonApi.filter(schema, actualData);

        assertNull(node.get("person").get(1).get("street"));
    }

    protected JsonSchema getSchemaFronString(final String schemaContent) {
        return JsonSchemaFactory.createJsonSchema(schemaContent);
    }

}
