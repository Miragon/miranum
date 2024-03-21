package io.miranum.platform.tasklist.application.service;

import io.miranum.platform.tasklist.application.port.in.WorkOnUserTaskUseCase;
import io.miranum.platform.tasklist.application.port.out.engine.TaskCommandPort;
import io.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkOnUserTaskService implements WorkOnUserTaskUseCase {
    private final TaskOutPort taskQueryPort;
    private final TaskCommandPort taskCommandPort;

    @Override
    public void completeUserTask(String user, String taskId, Map<String, Object> payload) {

    }

    @Override
    public void saveUserTask(String user, String taskId, Map<String, Object> payload) {

    }

    @Override
    public void assignUserTask(String user, String taskId, String assignee) {

    }

    @Override
    public void unassignUserTask(String user, String taskId) {

    }

    @Override
    public void cancelUserTask(String user, String taskId) {
        val task = taskQueryPort.getTask(user, taskId);
        //   if (cancellationFlagOutPort.apply(task)) {
        taskCommandPort.cancelUserTask(taskId);
        //   } else {
        //      throw new IllegalArgumentException("Task " + taskId + " can not be cancelled.");
        //  }
    }


}
