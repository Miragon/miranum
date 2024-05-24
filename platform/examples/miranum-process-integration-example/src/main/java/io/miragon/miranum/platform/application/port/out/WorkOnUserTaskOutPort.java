package io.miragon.miranum.platform.application.port.out;

import io.miragon.miranum.connect.task.api.exception.TaskAccessDeniedException;

import java.util.Map;

public interface WorkOnUserTaskOutPort {

    void completeUserTask(String user, String taskId, Map<String, Object> payload) throws TaskAccessDeniedException;

    void assignUserTask(String user, String taskId, String assignee) throws TaskAccessDeniedException;

    void unassignUserTask(String user, String taskId) throws TaskAccessDeniedException;

}
