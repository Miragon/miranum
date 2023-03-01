/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miragon.miranum.connect.json.registry.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to perform db operation on a {@link SchemaEntity}
 *
 * @author externer.dl.horn
 */
public interface SchemaRepository extends JpaRepository<SchemaEntity, String> {

    Optional<SchemaEntity> findByKey(String key);
}
