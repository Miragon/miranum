package io.miragon.miranum.platform.tasklist.application.port.in;


import io.miragon.miranum.platform.tasklist.domain.Task;

import java.util.List;

public interface UserTaskQuery {

    List<Task> getTasksForUserGroup(final String group, final String user);

    List<Task> getTasksForUser(final String user);

    Task getTask(final String user, final String taskId);
}
