package io.miragon.miranum.platform.tasklist.application.port.out.task;

import io.miragon.miranum.platform.tasklist.domain.AssignUserTask;

import java.util.List;

public interface AssignTaskOutPort {

    void assignUserTask(final AssignUserTask assignUserTask, final String user, final List<String> userGroups);

    void unassignUserTask(final String taskId, final String user, final List<String> userGroups);

}
