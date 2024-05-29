package io.miragon.miranum.platform.tasklist.application.port.in;

import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;

public interface WorkOnTaskUseCase {


    void assignUserTask(String user, String taskId, String assignee) throws TaskAccessDeniedException;

    void unassignUserTask(String user, String taskId) throws TaskAccessDeniedException;

}
