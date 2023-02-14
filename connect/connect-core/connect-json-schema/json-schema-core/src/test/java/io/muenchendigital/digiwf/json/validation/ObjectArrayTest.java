package io.muenchendigital.digiwf.json.validation;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static io.muenchendigital.digiwf.json.utils.JsonSchemaTestUtils.getSchemaString;

public class ObjectArrayTest {

    private JsonSchemaValidator validationService;

    @BeforeEach
    private void setUp() {
        this.validationService = new JsonSchemaValidator();
    }

    @Test
    public void object_list_with_valid() throws URISyntaxException, IOException {
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
