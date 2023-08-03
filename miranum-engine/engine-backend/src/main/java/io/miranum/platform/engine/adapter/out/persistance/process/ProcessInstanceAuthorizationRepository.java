package io.miranum.platform.engine.adapter.out.persistance.process;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to perform db operation on a {@link ProcessInstanceAuthorizationEntity}
 *
 * @author externer.dl.horn
 */
public interface ProcessInstanceAuthorizationRepository extends JpaRepository<ProcessInstanceAuthorizationEntity, String> {

    Optional<ProcessInstanceAuthorizationEntity> findByUserIdAndProcessInstanceId(String userId, String processInstanceId);
}
