package io.miranum.platform.tasklist.application.port.in;

import java.util.Map;


public interface WorkOnUserTaskUseCase {

    void completeUserTask(String user, String taskId, Map<String, Object> payload);

    void saveUserTask(String user, String taskId, Map<String, Object> payload);

    void assignUserTask(String user, String taskId, String assignee);

    void unassignUserTask(String user, String taskId);

    void cancelUserTask(String user, String taskId);
}
