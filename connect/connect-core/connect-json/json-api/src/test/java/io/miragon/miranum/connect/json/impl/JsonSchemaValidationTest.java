package io.miragon.miranum.connect.json.impl;

import io.miragon.miranum.connect.json.api.JsonSchema;
import io.miragon.miranum.connect.json.api.ValidationResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static io.miragon.miranum.connect.json.utils.JsonSchemaTestUtils.getSchemaString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonSchemaValidationTest {


    @Test
    public void test_readonly_error() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.of(
                "stringProp1", "fsdafsda",
                "numberProp1", 1,
                "booleanprop", false
        );

        final Map<String, Object> previousData = Map.of(
                "dateprop", "20",
                "numberProp1", 1,
                "booleanprop", true
        );

        final List<ValidationResult> result = schema.validate(actualData, previousData);
        assertEquals(1, result.size());
    }

    @Test
    public void test_data_valid() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.of(
                "stringProp1", "fsdafsda",
                "numberProp1", 1
        );

        final Map<String, Object> previousData = Map.of(
                "dateprop", "20",
                "numberProp1", 1,
                "booleanprop", true
        );

        final List<ValidationResult> result = schema.validate(actualData, previousData);
        assertEquals(0, result.size());
    }

    @Test
    public void test_additional_data() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.of(
                "stringProp1", "fsdafsda",
                "numberProp1", 1,
                "notInSchemaKey", 1
        );

        final Map<String, Object> previousData = Map.of(
                "dateprop", "20",
                "numberProp1", 1,
                "booleanprop", true
        );

        final List<ValidationResult> result = schema.validate(actualData, previousData);

        assertEquals(2, result.size());
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
                        "mail", "test@mail.de")

        );

        final List<ValidationResult> result = schema.validate(actualData);
        assertEquals(0, result.size());
    }

    @Test
    public void textarea_with_maxlength_is_valid() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "numberProp1", 12,
                "GrundDienstlNotwendigkeit", "fdsfsdafsdafadsfsadfsdafd"
        );
        final String rawSchema = getSchemaString("/schema/text-area-schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);


        final List<ValidationResult> results = schema.validate(acutalData);

        assertEquals(0, results.size());
    }

    @Test
    public void one_of_is_valid() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "stringProp1", "stringprop"
        );
        final String rawSchema = getSchemaString("/schema/one-of-schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final List<ValidationResult> results = schema.validate(acutalData);

        assertEquals(0, results.size());
    }

    @Test
    public void one_of_is_invalid() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "stringProp3", "stringprop"
        );
        final String rawSchema = getSchemaString("/schema/one-of-schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final List<ValidationResult> results = schema.validate(acutalData);

        assertEquals(2, results.size());
    }

    @Test
    public void select_schema_with_wrong_data_fails() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "stringProp1", "fdsfsdafsdafadsfsadfsdafd",
                "selection", "test3"
        );
        final String rawSchema = getSchemaString("/schema/complex-schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);
        final List<ValidationResult> results = schema.validate(acutalData);

        assertEquals(2, results.size());
    }

    @Test
    public void object_schema_list_valid() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/object-list-schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.of(
                "person", List.of(
                        Map.of(
                                "vorname", "Dom",
                                "nachname", "Hrn")
                )
        );

        final List<ValidationResult> result = schema.validate(actualData);
        assertEquals(0, result.size());
    }

    @Test
    public void object_schema_list_invalid() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/object-list-schema.json");
        final JsonSchema schema = this.getSchemaFronString(rawSchema);

        final Map<String, Object> actualData = Map.of(
                "person", List.of(
                        Map.of(
                                "vorname", "Dom",
                                "nachname", "Hrn"),
                        Map.of(
                                "vorname", "Dom",
                                "street", "InvalidStreet",
                                "nachname", "Hrn")
                )
        );

        final List<ValidationResult> result = schema.validate(actualData);
        assertEquals(1, result.size());
    }

    protected JsonSchema getSchemaFronString(final String schemaContent) {
        return JsonSchemaFactory.createJsonSchema(schemaContent);
    }

}
