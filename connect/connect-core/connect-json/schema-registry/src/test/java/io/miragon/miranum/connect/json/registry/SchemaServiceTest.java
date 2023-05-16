package io.miragon.miranum.connect.json.registry;

import io.miragon.miranum.connect.json.api.JsonApi;
import io.miragon.miranum.connect.json.api.JsonSchema;
import io.miragon.miranum.connect.json.registry.application.service.SchemaService;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SchemaServiceTest {

    @Autowired
    private SchemaService schemaService;

    @Autowired
    private JsonApi jsonApi;

    @Test
    @Order(1)
    public void shouldThrowValidationException() {
        Exception exception = assertThrows(ConstraintViolationException.class, () ->
                this.schemaService.saveSchema("test", null)
        );

        assertEquals("saveSchema.arg0.jsonSchema: must not be null", exception.getMessage());
    }

    @Test
    @Order(2)
    public void shouldSaveSchema() throws IOException, URISyntaxException {
        final String rawSchema = getSchemaString("/schema/schema.json");
        final JsonSchema schema = jsonApi.buildSchema(rawSchema);
        final Schema saved = this.schemaService.saveSchema("test", schema.getSchema());

        assertEquals(36, saved.getId().length());
        assertEquals(1, saved.getVersion());
        assertEquals("test", saved.getRef());
        assertEquals(schema.getSchema().toString(), saved.getJsonSchema().toString());
    }

    public static String getSchemaString(final String path) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(SchemaServiceTest.class.getResource(path).toURI())));
    }
}
