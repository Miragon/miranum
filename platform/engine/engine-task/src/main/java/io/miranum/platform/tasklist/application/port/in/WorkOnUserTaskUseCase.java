package io.miranum.platform.tasklist.application.port.in;

import io.miranum.platform.tasklist.domain.TaskData;

import java.util.Map;


public interface WorkOnUserTaskUseCase {


    TaskData loadUserTask(String user, String taskId);

    void completeUserTask(String user, String taskId, Map<String, Object> payload);

    void saveUserTask(String user, String taskId, Map<String, Object> payload);

    void assignUserTask(String user, String taskId, String assignee);

    void unassignUserTask(String user, String taskId);

    void cancelUserTask(String taskId);
}
