package io.miranum.platform.engine.processinstance.infrastructure.repository;

import io.miranum.platform.engine.processinstance.infrastructure.entity.ServiceInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository to perform db operation on a {@link ServiceInstanceEntity}
 *
 * @author externer.dl.horn
 */
public interface ProcessInstanceInfoRepository extends JpaRepository<ServiceInstanceEntity, String> {

    Optional<ServiceInstanceEntity> findByInstanceId(String processInstanceId);

    List<ServiceInstanceEntity> findAllByInstanceIdIn(List<String> instanceIds);

    List<ServiceInstanceEntity> findByRemovalTimeBefore(Date referenceDate);
}
