package io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskInfoRepository extends JpaRepository<TaskInfoEntity, String> {

    List<TaskInfoEntity> findByAssignee(final String assignee);

    List<TaskInfoEntity> findByAssigneeOrAuthorities_typeAndAuthorities_value(final String assignee, final String type, final String value);

    List<TaskInfoEntity> findByAuthorities_typeAndAuthorities_value(final String type, final String value);

}
