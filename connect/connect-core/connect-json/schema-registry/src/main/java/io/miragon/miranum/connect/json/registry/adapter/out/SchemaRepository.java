package io.miragon.miranum.connect.json.registry.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchemaRepository extends JpaRepository<SchemaEntity, String> {

    List<SchemaEntity> findAllByRef(String ref);
    Optional<SchemaEntity> findByRefAndVersion(String ref, Integer version);
}
