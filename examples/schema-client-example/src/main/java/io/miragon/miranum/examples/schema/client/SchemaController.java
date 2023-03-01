package io.miragon.miranum.examples.schema.client;

import com.fasterxml.jackson.databind.JsonNode;
import io.miragon.miranum.connect.json.api.JsonApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schema")
@AllArgsConstructor
public class SchemaController {

    private final JsonApi jsonApi;

    @GetMapping("/{ref}")
    public ResponseEntity<JsonNode> getSchema(@PathVariable final String ref) {
        return ResponseEntity.ok(this.jsonApi.getSchema(ref).getSchema());
    }
}
