package io.miranum.platform.engine.jsonschema.domain.repository;

import io.miranum.platform.engine.jsonschema.domain.model.JsonSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to perform db operation on a {@link JsonSchema}
 *
 * @author externer.dl.horn
 */
public interface JsonSchemaRepository extends JpaRepository<JsonSchema, String> {

    Optional<JsonSchema> findByKey(String key);
}
