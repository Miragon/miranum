package io.miranum.platform.engine.adapter.out.persistance.process;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Repository to perform db operation on a {@link MiranumProcessInstanceEntity}
 *
 * @author externer.dl.horn
 */
public interface MiranumProcessInstanceRepository extends JpaRepository<MiranumProcessInstanceEntity, String> {

    @Query("SELECT p FROM MiranumProcessInstance p JOIN MiranumInstanceAuth a on p.id = a.processInstanceId WHERE a.userId = :userId ")
    List<MiranumProcessInstanceEntity> findAllByUserId(@Param("userId") String userId);

    List<MiranumProcessInstanceEntity> findByRemovalTimeBefore(Date referenceDate);
}
