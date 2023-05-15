/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miragon.miranum.connect.json.registry.adapter.in;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.registry.application.ports.in.ReadSchemaUseCase;
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
     * @param ref  reference to the json schema
     * @param node Schema that is created
     * @return json schema
     */
    @PostMapping("/{ref}")
    @Operation(description = "create new schema")
    public ResponseEntity<Void> createSchema(@PathVariable final String ref, @RequestBody @Valid final JsonNode node) {
        this.createSchemaUseCase.saveSchema(ref, node);
        return ResponseEntity.ok().build();
    }

    /**
     * Get a schema by ref
     *
     * @param ref reference to the schema
     * @return schema
     */
    @GetMapping("/{ref}")
    @Operation(description = "get latest schema by ref")
    public ResponseEntity<JsonNode> getLatestSchema(@PathVariable final String ref) {
        try {
            final Schema schema = this.readSchemaUseCase.loadLatestSchema(ref);
            return ResponseEntity.ok(schema.getJsonSchema());
        } catch (final NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }


}
