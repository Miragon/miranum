package io.miranum.platform.engine.processinstance.infrastructure.repository;

import io.miranum.platform.engine.processinstance.infrastructure.entity.ServiceInstanceAuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to perform db operation on a {@link ServiceInstanceAuthorizationEntity}
 *
 * @author externer.dl.horn
 */
public interface ProcessInstanceAuthorizationRepository extends JpaRepository<ServiceInstanceAuthorizationEntity, String> {

    List<ServiceInstanceAuthorizationEntity> findAllByUserId(String userId);

    Optional<ServiceInstanceAuthorizationEntity> findByUserIdAndProcessInstanceId(String userId, String processInstanceId);
}
