package io.miragon.miranum.connect.json.registry;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.api.JsonApi;
import io.miragon.miranum.connect.json.api.JsonSchema;
import io.miragon.miranum.connect.json.impl.JsonSchemaFactory;
import io.miragon.miranum.connect.json.registry.application.service.SchemaService;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SchemaServiceTest {

    @Autowired
    private SchemaService schemaService;

    private static Map<String, JsonSchema> schemas = new HashMap<>();

    @BeforeAll
    public static void setup() throws IOException, URISyntaxException {
        final String rawSchema1 = getSchemaString("/schema/schema1.json");
        final String rawSchema2 = getSchemaString("/schema/schema2.json");
        schemas.put("schema1", JsonSchemaFactory.createJsonSchema(rawSchema1));
        schemas.put("schema2", JsonSchemaFactory.createJsonSchema(rawSchema2));
    }

    @Test
    @Order(1)
    public void shouldThrowValidationException() {
        Exception exception = assertThrows(ConstraintViolationException.class, () ->
                this.schemaService.saveSchema("test", null)
        );

        assertEquals("saveSchema.arg0.jsonSchema: must not be null", exception.getMessage());
    }

    @Test
    @Order(1)
    @Rollback(false)
    public void shouldSaveSchema1(){
        final JsonNode jsonNode = schemas.get("schema1").getSchema();
        final Schema saved = this.schemaService.saveSchema("test", jsonNode);

        assertEquals(36, saved.getId().length());
        assertEquals(1, saved.getVersion());
        assertEquals("test", saved.getRef());
        assertEquals(jsonNode.toString(), saved.getJsonSchema().toString());
    }

    @Test
    @Order(2)
    public void shouldLoadLatest_expectingSchema1() {
        final Schema saved = this.schemaService.loadLatestSchema("test");

        assertEquals(36, saved.getId().length());
        assertEquals(1, saved.getVersion());
        assertEquals("test", saved.getRef());
        assertEquals(schemas.get("schema1").getSchema().toString(), saved.getJsonSchema().toString());
    }

    @Test
    @Order(3)
    @Rollback(false)
    public void shouldSaveSchema2(){
        final JsonNode jsonNode = schemas.get("schema2").getSchema();
        final Schema saved = this.schemaService.saveSchema("test", jsonNode);

        assertEquals(36, saved.getId().length());
        assertEquals(2, saved.getVersion());
        assertEquals("test", saved.getRef());
        assertEquals(jsonNode.toString(), saved.getJsonSchema().toString());
    }

    @Test
    @Order(4)
    public void shouldLoadLatest_expectingSchema2() {
        final Schema saved = this.schemaService.loadLatestSchema("test");

        assertEquals(36, saved.getId().length());
        assertEquals(2, saved.getVersion());
        assertEquals("test", saved.getRef());
        assertEquals(schemas.get("schema2").getSchema().toString(), saved.getJsonSchema().toString());
    }

    @Test
    @Order(4)
    public void shouldLoadVersionedSchema() {
        final Schema saved = this.schemaService.loadVersionedSchema("test", 1);

        assertEquals(36, saved.getId().length());
        assertEquals(1, saved.getVersion());
        assertEquals("test", saved.getRef());
        assertEquals(schemas.get("schema1").getSchema().toString(), saved.getJsonSchema().toString());
    }


    public static String getSchemaString(final String path) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(SchemaServiceTest.class.getResource(path).toURI())));
    }
}
