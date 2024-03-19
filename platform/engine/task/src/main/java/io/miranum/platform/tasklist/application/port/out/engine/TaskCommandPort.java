package io.miranum.platform.tasklist.application.port.out.engine;

import java.time.Instant;
import java.util.Map;

/**
 * Port to send commands to the process engine.
 */
public interface TaskCommandPort {

  /**
   * Complete user task.
   * @param taskId id of the task.
   * @param payload payload to use on completion.
   */
  void completeUserTask(String taskId, Map<String, Object> payload);

  /**
   * Stores input of the user task.
   * @param taskId id of the task.
   * @param payload payload variables to store.
   */
  void saveUserTask(String taskId, Map<String, Object> payload);

  /**
   * Assigns user task to a user.
   * @param taskId id of the task.
   * @param assignee user id of the user.
   * @deprecated should not be used anymore.
   */
  @Deprecated
  void assignUserTask(String taskId, String assignee);

  /**
   * Unassigns a user task.
   * @param taskId id of the task.
   * @deprecated should not be used anymore.
   */
  void unassignUserTask(String taskId);

  /**
   * Sets user task as deferred until a given date.
   * @param taskId task id to defer.
   * @param toInstant target date.
   */
  void deferUserTask(String taskId, Instant toInstant);

  /**
   * Undeferes the user task.
   * @param taskId task id to undefer.
   */
  void undeferUserTask(String taskId);

  /**
   * Cancel user task.
   * @param taskId task id of the task.
   */
  void cancelUserTask(String taskId);
}
