/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miragon.miranum.connect.json.registry.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SchemaRepository extends JpaRepository<SchemaEntity, String> {

    @Query(value = "SELECT max(version) FROM SchemaEntity WHERE key = ?1")
    Optional<SchemaEntity> findLatestByKey(String key);

    Optional<SchemaEntity> findByKeyAndVersion(String key, Integer version);
}
