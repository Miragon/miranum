package io.miranum.platform.engine.processconfig.infrastructure.repository;

import io.miranum.platform.engine.processconfig.infrastructure.entity.ProcessConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to perform db operation on a {@link ProcessConfigEntity}
 *
 * @author externer.dl.horn
 */
public interface ProcessConfigRepository extends JpaRepository<ProcessConfigEntity, String> {

    Optional<ProcessConfigEntity> findByKey(String key);

    List<ProcessConfigEntity> findByKeyIn(Iterable<String> keys);
}
