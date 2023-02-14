package io.muenchendigital.digiwf.json.serialization;


import io.muenchendigital.digiwf.json.serialization.serializer.JsonSerializer;
import io.muenchendigital.digiwf.json.serialization.serializer.JsonSerializerImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static io.muenchendigital.digiwf.json.utils.JsonSchemaTestUtils.areEqual;
import static io.muenchendigital.digiwf.json.utils.JsonSchemaTestUtils.getSchemaString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonSchemaSerializationTest {

    private JsonSerializationService jsonSchemaSerializationService;

    @BeforeEach
    private void setUp() {
        final JsonSerializer jsonSerializer = new JsonSerializerImpl();
        this.jsonSchemaSerializationService = new JsonSerializationService(jsonSerializer);
    }

    @Test
    public void serialize_and_filter_data_with_simple_schema() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/simpleSchema.json");

        final Map<String, Object> source = Map.of(
                "stringProp1", "stringValue",
                "numberProp1", 12
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);
        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject());

        final Map<String, Object> expectedData = Map.of(
                "stringProp1", "stringValue"
        );

        assertThat(serializedData).isEqualTo(expectedData);
    }

    @Test
    public void serialize_and_filter_data_with_complex_allOf_schema() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/schema.json");

        final Map<String, Object> actualData = Map.of(
                "stringProp1", "fsdafsda"
        );

        final Map<String, Object> previousData = Map.of(
                "dateprop", "20"
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, actualData, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(previousData));

        final Map<String, Object> expectedData = Map.of("stringProp1", "fsdafsda");

        //override all
        assertThat(serializedData).isEqualTo(expectedData);
    }


    @Test
    public void serialize_data_and_ignore_readonly_values() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/simpleSchema.json");

        final Map<String, Object> actualData = Map.of(
                "stringProp1", "stringValue",
                "numberProp1", 12
        );

        final Map<String, Object> previousData = Map.of(
                "numberProp1", 100,
                "stringProp2", "100"
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, actualData, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(previousData));

        final Map<String, Object> expectedData = new HashMap<>();

        expectedData.put("stringProp1", "stringValue");
        expectedData.put("numberProp1", 100);

        //override all
        assertThat(serializedData).isEqualTo(expectedData);
    }

    @Test
    public void serialize_and_add_initialize_simple_schema() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/simpleSchema.json");

        final Map<String, Object> actualData = Map.of(
                "stringProp1", "stringValue"
        );

        final Map<String, Object> previousData = Map.of(
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, actualData, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(previousData));
        final JSONObject defaultValue = this.jsonSchemaSerializationService.initialize(new JSONObject(rawSchema).toString());
        final Map<String, Object> serializedDataWithDefaultValues = this.jsonSchemaSerializationService.merge(new JSONObject(serializedData), defaultValue);

        final Map<String, Object> expectedData = new HashMap<>();

        expectedData.put("stringProp1", "stringValue");
        expectedData.put("stringProp2", "");

        //override all
        assertThat(serializedDataWithDefaultValues).isEqualTo(expectedData);
    }

    @Test
    public void serialize_allOf_schema_and_igonre_readonly_values() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/schema.json");

        final Map<String, Object> actualData = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "",
                "numberProp1", 12
        );

        final Map<String, Object> previousData = Map.of(
                "numberProp1", 100,
                "stringProp1", "stringProp1"
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, actualData, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(previousData));

        final Map<String, Object> expectedData = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "",
                "numberProp1", 100
        );

        assertThat(serializedData).isEqualTo(expectedData);
    }


    @Test
    public void serialize_allOf_object_schema() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> actualData = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 12,
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final Map<String, Object> previousData = Map.of(
                "numberProp1", 100
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, actualData, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(previousData));

        final Map<String, Object> expectedData = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test"
                ));

        assertThat(new JSONObject(serializedData).toString()).isEqualTo(new JSONObject(expectedData).toString());
    }


    @Test
    public void filter_unknown_values_in_allOf_schema() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> source = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 12,
                "balbla", 12,
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> expectedData = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        assertThat(filteredData.toMap()).isEqualTo(expectedData);
    }

    @Test
    public void add_null_values_if_no_value_is_present() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> actualData = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, actualData, true);

        assertThat(filteredData.get("textarea1")).isEqualTo(null);
    }

    @Test
    public void merge_complex_objects() {

        final Map<String, Object> actualData = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final Map<String, Object> previousData = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test1",
                        "stringProp2", "test2"
                )
        );

        final Map<String, Object> mergedData = this.jsonSchemaSerializationService.merge(new JSONObject(actualData), new JSONObject(previousData));

        final Map<String, Object> expectedData = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test",
                        "stringProp2", "test2"
                )
        );

        assertThat(mergedData).isEqualTo(expectedData);
    }

    @Test
    public void merge_complex_objects_with_schema() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> actualData = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "stringProp5", "stringValue",
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, actualData, true);
        final Map<String, Object> previousData = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test1",
                        "stringProp2", "test2"
                )
        );

        final Map<String, Object> mergedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(previousData));
        final Map<String, Object> expectedData = Map.of(
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test",
                        "stringProp2", "test2"
                )
        );

        assertThat(mergedData).isEqualTo(expectedData);
    }

    @Test
    public void serialize_combined_object_schema_with_previous_data() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> actualData = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 12,
                "objectProp", Map.of(
                        "stringProp1", "test"
                )
        );

        final Map<String, Object> previousData = Map.of(
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp2", "test"
                )
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, actualData, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject(previousData));

        final Map<String, Object> expectedData = Map.of(
                "textarea1", "textAreaValue",
                "booleanprop", true,
                "dateprop", "2020-10-1",
                "stringProp1", "stringValue",
                "numberProp1", 100,
                "objectProp", Map.of(
                        "stringProp1", "test",
                        "stringProp2", "test"
                ));

        assertThat(new JSONObject(serializedData).toString()).isEqualTo(new JSONObject(expectedData).toString());
    }


    @Test
    public void serialize_data_with_custom_type_schema() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/customTypesSchema.json");

        final Map<String, Object> source = Map.of(
                "FormField_Grusstext", "meinValue"
        );

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject());

        assertThat(serializedData).isEqualTo(Map.of(
                "FormField_Grusstext", "meinValue"
        ));
    }

    @Test
    public void serialize_complex_object_structure() throws URISyntaxException, IOException {
        final Map<String, Object> source = Map.of(
                "textarea", "100",
                "textfeld", "100",
                "objekt1", Map.of(
                        "objektTextfeld", "fdsfsdafsdafadsfsadfsdafd",
                        "objektSchalter", true)
        );

        final String rawSchema = getSchemaString("/schema/validation/complexObjectSchema.json");

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);

        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject());

        final Map<String, Object> erg = Map.of(
                "textarea", "100",
                "textfeld", "100",
                "objekt1", Map.of(
                        "objektTextfeld", "fdsfsdafsdafadsfsadfsdafd",
                        "objektSchalter", true));

        assertThat(areEqual(erg, serializedData)).isEqualTo(true);
    }

    @Test
    public void serialize_conditional_subschema() throws URISyntaxException, IOException {
        final Map<String, Object> source = Map.of(
                "stringProp1", "100"
        );

        final String rawSchema = getSchemaString("/schema/validation/conditionalSubSchema.json");

        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, source, true);
        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(filteredData, new JSONObject());

        final Map<String, Object> erg = Map.of(
                "stringProp1", "100");

        assertThat(areEqual(erg, serializedData)).isEqualTo(true);
    }

    @Test
    public void generate_object_structure_from_json_pointer() {
        final JSONObject object = this.jsonSchemaSerializationService.generateValue("#/antragsdaten/datumAntragstellung/stringProp1", "testValue");
        assertEquals(object.toString(), "{\"antragsdaten\":{\"datumAntragstellung\":{\"stringProp1\":\"testValue\"}}}");
    }

    @Test
    public void extract_value_by_json_pointer() {
        final JSONObject object = new JSONObject("{\"antragsdaten\":{\"datumAntragstellung\":{\"stringProp1\":\"testValue\"}}}");
        final Object value = this.jsonSchemaSerializationService.extractValue(object.toMap(), "#/antragsdaten/datumAntragstellung/stringProp1");
        assertEquals(value, "testValue");
    }

    @Test
    public void extract_value_by_json_pointer_not_present() {
        final JSONObject object = new JSONObject("{\"antragsdaten\":{\"datumAntragstellung\":{\"stringProp2\":\"testValue\"}}}");
        final Object value = this.jsonSchemaSerializationService.extractValue(object.toMap(), "#/antragsdaten/datumAntragstellung/stringProp1");
        assertNull(value);
    }

    @Test
    public void initalize_and_merge_object_schema() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/objectSchema.json");

        final Map<String, Object> actualData = Map.of(
                "booleanprop", true,
                "numberProp1", 12,
                "objectProp", Map.of(
                        "stringProp1", "abc",
                        "textarea1", "xyz"
                )
        );

        final JSONObject initializedObject = this.jsonSchemaSerializationService.initialize(rawSchema);
        final Map<String, Object> mergedData = this.jsonSchemaSerializationService.merge(new JSONObject(actualData), initializedObject);

        final Map<String, Object> expectedData = Map.of(
                "booleanprop", true,
                "dateprop", "",
                "numberProp1", 12,
                "objectProp", Map.of(
                        "stringProp1", "abc",
                        "textarea1", "xyz"
                ),
                "stringProp1", "",
                "textarea1", ""
        );

        assertThat(mergedData).isEqualTo(expectedData);
    }

    @Test
    public void initialize_simple_schema() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/simpleSchema.json");

        final JSONObject initializedObject = this.jsonSchemaSerializationService.initialize(rawSchema);

        final Map<String, Object> expectedData = Map.of(
                "stringProp1", "",
                "stringProp2", ""
        );

        assertThat(initializedObject.toMap()).isEqualTo(expectedData);
    }

    @Test
    public void initialize_simple_schema_with_readonly_false() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/simpleSchemaWithReadonlyFalse.json");

        final JSONObject initializedObject = this.jsonSchemaSerializationService.initialize(rawSchema);
        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, initializedObject.toMap(), true);

        final Map<String, Object> expectedData = Map.of(
                "stringProp1", "",
                "stringProp2", ""
        );

        assertThat(filteredData.toMap()).isEqualTo(expectedData);
    }

    @Test
    public void serialize_and_add_initialize_simple_schema_with_json_null() throws URISyntaxException, IOException {
        final String rawSchema = getSchemaString("/schema/serialization/simpleSchemaWithReadonlyFalse.json");

        final Map<String, Object> actualData = Map.of(
                "stringProp1", "stringValue"
        );

        final JSONObject previosData = this.jsonSchemaSerializationService.initialize(rawSchema);
        final JSONObject filteredData = this.jsonSchemaSerializationService.filter(rawSchema, actualData, true);
        final Map<String, Object> clearedData = this.jsonSchemaSerializationService.merge(filteredData, previosData);
        final JSONObject defaultValue = this.jsonSchemaSerializationService.initialize(rawSchema);
        final Map<String, Object> serializedData = this.jsonSchemaSerializationService.merge(new JSONObject(clearedData), defaultValue);

        final Map<String, Object> expectedData = new HashMap<>();
        expectedData.put("stringProp1", "stringValue");
        expectedData.put("stringProp2", "");

        //override all
        assertThat(serializedData).isEqualTo(expectedData);
    }

    //------------------------------------ Helper Methods ------------------------------------//

}
