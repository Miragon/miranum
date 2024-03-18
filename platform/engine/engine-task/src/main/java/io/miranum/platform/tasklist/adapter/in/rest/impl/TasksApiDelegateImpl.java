package io.miranum.platform.tasklist.adapter.in.rest.impl;

import io.miranum.platform.tasklist.adapter.in.rest.api.TasksApiDelegate;
import io.miranum.platform.tasklist.adapter.in.rest.mapper.TaskMapper;
import io.miranum.platform.tasklist.adapter.in.rest.model.PageOfTasksTO;
import io.miranum.platform.tasklist.application.port.in.RetrieveTasksForUser;
import io.miranum.platform.tasklist.domain.PagingAndSorting;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Tasks API delegate calling the Retrieve Tasks Use Case.
 */
@Component
@RequiredArgsConstructor
public class TasksApiDelegateImpl implements TasksApiDelegate {

  private final TaskMapper taskMapper;
  private final RetrieveTasksForUser retrieveTasksForUser;

  @Override
  public ResponseEntity<PageOfTasksTO> getCurrentUserTasks(Integer page, Integer size, String query, LocalDate followUp, String sort) {
    var pagingAndSorting = new PagingAndSorting(page, size, sort);
    var result = retrieveTasksForUser.getTasksForCurrentUser(query, followUp, pagingAndSorting);
    return ok(taskMapper.toSchemaTO(result));
  }

  @Override
  public ResponseEntity<PageOfTasksTO> getAssignedGroupTasks(Integer page, Integer size, String query, String sort) {
    var pagingAndSorting = new PagingAndSorting(page, size, sort);
    var result = retrieveTasksForUser.getAssignedTasksForCurrentUserGroup(query, pagingAndSorting);
    return ok(taskMapper.toSchemaTO(result));
  }

  @Override
  public ResponseEntity<PageOfTasksTO> getUnassignedGroupTasks(Integer page, Integer size, String query, String sort) {
    var pagingAndSorting = new PagingAndSorting(page, size, sort);
    var result = retrieveTasksForUser.getUnassignedTasksForCurrentUserGroup(query, pagingAndSorting);
    return ok(taskMapper.toSchemaTO(result));
  }
}
