package io.muenchendigital.digiwf.json.validation;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static io.muenchendigital.digiwf.json.utils.JsonSchemaTestUtils.getSchemaMap;
import static io.muenchendigital.digiwf.json.utils.JsonSchemaTestUtils.getSchemaString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonValidatorTest {

    private JsonSchemaValidator validationService;

    @BeforeEach
    private void setUp() {
        this.validationService = new JsonSchemaValidator();
    }

    @Test
    public void simple_schema_is_valid() throws URISyntaxException, IOException {
        final Map<String, Object> data = Map.of(
                "numberProp1", 12,
                "stringProp1", "fdsfsdafsdafadsfsadfsdafd"
        );
        final String rawSchema = getSchemaString("/schema/validation/simpleSchema.json");
        this.validationService.validate(new JSONObject(rawSchema).toMap(), data);
    }

    @Test
    public void regex_schema_with_invalid_data_fails() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "numberProp1", 12,
                "stringProp1", "fdsfsdafsdafadsfsadfsdafdfdsfsdafsdafadsfsadfsdafd"
        );
        final String rawSchema = getSchemaString("/schema/validation/simpleSchema.json");
        final DigiWFValidationException exception = assertThrows(DigiWFValidationException.class, () -> {
            this.validationService.validate(new JSONObject(rawSchema).toMap(), acutalData);
        });

        assertThat(exception.getMessage()).isEqualTo("ValidationErrorInformation(pointer=#/stringProp1, schemaPath=#/properties/stringProp1, violatedSchema={\"type\":\"string\",\"pattern\":\"^.{1,30}$\"}, message=#/stringProp1: string [fdsfsdafsdafadsfsadfsdafdfdsfsdafsdafadsfsadfsdafd] does not match pattern ^.{1,30}$)");
    }

    @Test
    public void basic_properties_are_valid() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "stringProp1", "fds",
                "textarea1", "fdsa",
                "numberProp1", 12,
                "booleanprop", false,
                "dateprop", "2020-10-10"
        );
        final String rawSchema = getSchemaString("/schema/validation/schema.json");

        this.validationService.validate(new JSONObject(rawSchema).toMap(), acutalData);
    }

    @Test
    public void required_schema_without_data_is_invalid() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "stringProp1", "fdsfsdafsdafadsfsadfsdafd"
        );
        final String rawSchema = getSchemaString("/schema/validation/simpleSchema.json");
        final DigiWFValidationException exception = assertThrows(DigiWFValidationException.class, () -> {
            this.validationService.validate(new JSONObject(rawSchema).toMap(), acutalData);
        });

        assertThat(exception.getMessage()).isEqualTo("ValidationErrorInformation(pointer=#, schemaPath=#, violatedSchema={\"additionalProperties\":false,\"required\":[\"numberProp1\"],\"properties\":{\"numberProp1\":{\"type\":\"number\"},\"stringProp1\":{\"type\":\"string\",\"pattern\":\"^.{1,30}$\"}}}, message=#: required key [numberProp1] not found)");
    }

    @Test
    public void object_schema_is_valid() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "numberProp1", 100,
                "objectProp1", Map.of(
                        "stringProp1", "fdsfsdafsdafadsfsadfsdafd",
                        "numberProp1", 12
                )
        );

        final String rawSchema = getSchemaString("/schema/validation/objectSchema.json");
        this.validationService.validate(new JSONObject(rawSchema).toMap(), acutalData);
    }

    @Test
    public void nested_allOf_schema_is_valid() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "textarea", "100",
                "textfeld", "100",
                "objekt1", Map.of(
                        "objektTextfeld", "fdsfsdafsdafadsfsadfsdafd",
                        "objektSchalter", true
                )
        );

        final String rawSchema = getSchemaString("/schema/validation/complexObjectSchema.json");
        this.validationService.validate(new JSONObject(rawSchema).toMap(), acutalData);
    }


    @Test
    public void textarea_with_maxlength_is_valid() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "numberProp1", 12,
                "GrundDienstlNotwendigkeit", "fdsfsdafsdafadsfsadfsdafd"
        );
        final String rawSchema = getSchemaString("/schema/validation/textAreaSchema.json");

        this.validationService.validate(getSchemaMap(rawSchema), acutalData);
    }

    @Test
    public void objekt_schema_without_additionalPropperties_fails() throws IOException, URISyntaxException {

        final String rawSchema = getSchemaString("/schema/validation/complexObjectSchemaAdditionalPropertiesFalse.json");
        final Map<String, Object> acutalData = Map.of(
                "textarea", "12",
                "objekt1", Map.of("objektTextfeld1", "abc")
        );

        final DigiWFValidationException exception = assertThrows(DigiWFValidationException.class, () -> {
            this.validationService.validate(new JSONObject(rawSchema).toMap(), acutalData);
        });

        assertThat(exception.getMessage()).contains("ValidationErrorInformation(pointer=#/objekt1, schemaPath=#/allOf/0/allOf/1/properties/objekt1, violatedSchema={\"type\":\"object\",\"additionalProperties\":false,\"title\":\"Dynamisches Objekt\",\"description\":\"fsdafsdafsad\",\"x-rules\":[],\"fieldType\":\"object\",\"x-options\":{\"fieldColProps\":{\"messages\":{},\"cols\":12,\"sm\":12}},\"key\":\"d6d8d7a1-e812-42ac-9135-8242f93b6158\",\"x-props\":{\"outlined\":true,\"dense\":true},\"properties\":{\"objektSchalter\":{\"title\":\"Schalter\",\"x-rules\":[],\"x-display\":\"switch\",\"x-options\":{\"fieldColProps\":{\"messages\":{},\"cols\":12,\"sm\":12}},\"fieldType\":\"switch\",\"key\":\"ObjektSchalter\",\"x-props\":{\"outlined\":true,\"dense\":true},\"type\":\"boolean\"},\"objektTextfeld\":{\"type\":\"string\",\"title\":\"Textfeld\",\"x-rules\":[],\"fieldType\":\"text\",\"x-options\":{\"fieldColProps\":{\"messages\":{},\"cols\":12,\"sm\":12}},\"key\":\"objektTextfeld\",\"x-props\":{\"outlined\":true,\"dense\":true}}}}, message=#/objekt1: extraneous key [objektTextfeld1] is not permitted)");

    }

    @Test
    public void select_schema_with_wrong_data_fails() throws URISyntaxException, IOException {
        final Map<String, Object> acutalData = Map.of(
                "stringProp1", "fdsfsdafsdafadsfsadfsdafd",
                "selection", "test3"
        );
        final String rawSchema = getSchemaString("/schema/validation/complexSchema.json");
        final DigiWFValidationException exception = assertThrows(DigiWFValidationException.class, () -> {
            this.validationService.validate(new JSONObject(rawSchema).toMap(), acutalData);
        });

        assertThat(exception.getValidationErrorInformation().size()).isEqualTo(8);
    }

    @Test
    public void array_list_is_valid() throws URISyntaxException, IOException {
        final Map<String, Object> data = Map.of(
                "ibanEinzahler", "MyEinzahler2",
                "betragInCent", 5,
                "FormField_Empfaenger", List.of("260"),
                "nameEinzahler", "MyEinzahler"
        );

        final String rawSchema = getSchemaString("/schema/validation/listObjectSchema.json");
        this.validationService.validate(new JSONObject(rawSchema).toMap(), data);
    }


}
