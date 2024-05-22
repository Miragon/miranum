package io.miragon.miranum.platform.tasklist.application.port.in;

import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;

import java.util.Map;


public interface WorkOnUserTaskUseCase {

    void completeUserTask(String user, String taskId, Map<String, Object> payload) throws TaskAccessDeniedException;

    void saveUserTask(String user, String taskId, Map<String, Object> payload) throws TaskAccessDeniedException;

    void assignUserTask(String user, String taskId, String assignee) throws TaskAccessDeniedException;

    void unassignUserTask(String user, String taskId) throws TaskAccessDeniedException;

    void cancelUserTask(String user, String taskId) throws TaskAccessDeniedException;
}
