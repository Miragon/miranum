package io.miragon.miranum.platform.application.port.in;

import io.miragon.miranum.connect.task.api.exception.TaskAccessDeniedException;

import java.util.Map;

public interface WorkOnUserTaskInPort {

    void completeUserTask(String user, String taskId, Map<String, Object> payload) throws TaskAccessDeniedException;

}
