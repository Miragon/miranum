package io.miranum.platform.tasklist.application.port.out.engine;

import java.time.Instant;
import java.util.Map;

/**
 * Port to send commands to the process engine.
 */
public interface TaskCommandPort {


    void completeTask(String taskId, Map<String, Object> payload);

    void saveUserTask(String taskId, Map<String, Object> payload);

    void assignUserTask(String taskId, String assignee);

    void unassignUserTask(String taskId);

    void deferUserTask(String taskId, Instant toInstant);

    void undeferUserTask(String taskId);
}
