package io.miragon.miranum.connect.json.registry.adapter.in;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.registry.application.ports.in.ReadSchemaUseCase;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaInCommand;
import io.miragon.miranum.connect.json.registry.application.ports.in.SaveSchemaUseCase;
import io.miragon.miranum.connect.json.registry.domain.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/**
 * Rest API to handle schemas.
 */
@Validated
@Transactional
@RestController
@RequestMapping("/schema")
@RequiredArgsConstructor
@Tag(name = "Schema", description = "API to handle schemas")
public class SchemaRestController {

    private final ReadSchemaUseCase readSchemaUseCase;
    private final SaveSchemaUseCase createSchemaUseCase;

    /**
     * Create a new schema.
     *
     * @param bundle  artefact bundle, the schema refers to
     * @param ref     reference to the json schema
     * @param schemaCreateDto    Schema with tags to create
     * @return json schema
     */
    @PostMapping("/{bundle}/{ref}")
    @Operation(description = "create new schema")
    public ResponseEntity<Void> createSchema(
            @PathVariable final String bundle,
            @PathVariable final String ref,
            @RequestBody @Valid final SchemaCreateDto schemaCreateDto) {
        this.createSchemaUseCase.saveSchema(
                new SaveSchemaInCommand(bundle, ref, schemaCreateDto.getTags(), schemaCreateDto.getJsonNode()));
        return ResponseEntity.ok().build();
    }

    /**
     * Get a schema by ref
     *
     * @param bundle  artefact bundle, the schema refers to
     * @param ref     reference to the schema
     * @return schema
     */
    @GetMapping("/{bundle}/{ref}")
    @Operation(description = "get latest schema by ref")
    public ResponseEntity<JsonNode> getLatestSchema(
            @PathVariable final String bundle,
            @PathVariable final String ref) {
        try {
            final Schema schema = this.readSchemaUseCase.loadLatestSchema(bundle, ref);
            return ResponseEntity.ok(schema.getJsonNode());
        } catch (final NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a schema by ref and version
     *s
     * @param bundle  artefact bundle, the schema refers to
     * @param ref     reference to the schema
     * @param tag     tag of the schema
     * @return schema
     */
    @GetMapping("/{bundle}/{ref}/{tag}")
    @Operation(description = "get latest schema by ref")
    public ResponseEntity<JsonNode> getLatestSchema(
            @PathVariable final String bundle,
            @PathVariable final String ref,
            @PathVariable final String tag) {
        try {
            final Schema schema = this.readSchemaUseCase.loadTaggedSchema(bundle, ref, tag);
            return ResponseEntity.ok(schema.getJsonNode());
        } catch (final NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }


}
