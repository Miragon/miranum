package io.miragon.miranum.connect.json.registry;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.api.JsonSchema;
import io.miragon.miranum.connect.json.impl.JsonSchemaFactory;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaInCommand;
import io.miragon.miranum.connect.json.registry.application.service.SchemaService;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import jakarta.validation.ConstraintViolationException;
import org.hamcrest.Matchers;
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
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                this.schemaService.saveSchema(
                        new SaveSchemaInCommand("onboarding", "new-employee-form", List.of("1.1"),  null))
        );

        assertEquals("saveSchema.arg0.jsonNode: must not be null", exception.getMessage());
    }

    @Test
    @Order(1)
    @Rollback(false)
    public void shouldSaveSchema1(){
        final JsonNode jsonNode = schemas.get("schema1").getSchema();
        final List<Schema> saved = this.schemaService.saveSchema(
                new SaveSchemaInCommand("onboarding", "new-employee-form", List.of("1.1", "latest"),  jsonNode));

        for (Schema schema : saved) {
            assertEquals(36, schema.getId().length());
            assertEquals("onboarding", schema.getBundle());
            assertEquals("new-employee-form", schema.getRef());
            assertEquals(jsonNode.toString(), schema.getJsonNode().toString());
        }

        assertThat(List.of("1.1", "latest"), Matchers.containsInAnyOrder(saved.stream().map(Schema::getTag).toArray()));
    }

    @Test
    @Order(2)
    public void shouldLoadLatest_expectingSchema1() {
        final Schema saved = this.schemaService.loadSchema("onboarding", "new-employee-form", "latest");

        assertEquals(36, saved.getId().length());
        assertEquals("latest", saved.getTag());
        assertEquals("onboarding", saved.getBundle());
        assertEquals("new-employee-form", saved.getRef());
        assertEquals(schemas.get("schema1").getSchema().toString(), saved.getJsonNode().toString());
    }

    @Test
    @Order(3)
    @Rollback(false)
    public void shouldSaveSchema2(){
        final JsonNode jsonNode = schemas.get("schema2").getSchema();
        final List<Schema> saved = this.schemaService.saveSchema(
                new SaveSchemaInCommand("onboarding", "new-employee-form", List.of("1.2", "latest"),  jsonNode));

        for (Schema schema : saved) {
            assertEquals(36, schema.getId().length());
            assertEquals("onboarding", schema.getBundle());
            assertEquals("new-employee-form", schema.getRef());
            assertEquals(jsonNode.toString(), schema.getJsonNode().toString());
        }

        assertThat(List.of("1.2", "latest"), Matchers.containsInAnyOrder(saved.stream().map(Schema::getTag).toArray()));
    }

    @Test
    @Order(4)
    public void shouldLoadLatest_expectingSchema2() {
        final Schema saved = this.schemaService.loadSchema( "onboarding", "new-employee-form", "latest");

        assertEquals(36, saved.getId().length());
        assertEquals("latest", saved.getTag());
        assertEquals("onboarding", saved.getBundle());
        assertEquals("new-employee-form", saved.getRef());
        assertEquals(schemas.get("schema2").getSchema().toString(), saved.getJsonNode().toString());
    }

    @Test
    @Order(4)
    public void shouldLoadVersionedSchema() {
        final Schema saved = this.schemaService.loadSchema("onboarding", "new-employee-form", "1.1");

        assertEquals(36, saved.getId().length());
        assertEquals("1.1", saved.getTag());
        assertEquals("onboarding", saved.getBundle());
        assertEquals("new-employee-form", saved.getRef());
        assertEquals(schemas.get("schema1").getSchema().toString(), saved.getJsonNode().toString());
    }


    public static String getSchemaString(final String path) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(SchemaServiceTest.class.getResource(path).toURI())));
    }
}
