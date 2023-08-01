package io.miranum.platform.engine.adapter.out.persistance.filter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository to perform db operation on a {@link FilterEntity}
 */
public interface FilterRepository extends JpaRepository<FilterEntity, String> {

    List<FilterEntity> findByUserId(String userId);
}
