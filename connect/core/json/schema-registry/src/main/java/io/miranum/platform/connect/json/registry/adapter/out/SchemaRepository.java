package io.miranum.platform.connect.json.registry.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchemaRepository extends JpaRepository<SchemaEntity, String> {

    List<SchemaEntity> findAllByBundleAndRef(String bundle, String ref);

    Optional<SchemaEntity> findByBundleAndRefAndTag(String bundle, String ref, String tag);
}
