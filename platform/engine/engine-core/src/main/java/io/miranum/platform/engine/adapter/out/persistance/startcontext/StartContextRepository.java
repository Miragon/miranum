/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miranum.platform.engine.adapter.out.persistance.startcontext;

import io.miranum.platform.engine.domain.process.StartContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to perform db operations on a {@link StartContext}
 *
 * @author externer.dl.horn
 */
public interface StartContextRepository extends JpaRepository<StartContextEntity, String> {

    Optional<StartContextEntity> findByUserIdAndDefinitionKey(String userId, String definitionKey);
}
