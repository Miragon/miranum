package io.miranum.platform.tasklist.application.port.in;


import io.miranum.platform.tasklist.domain.Task;

import java.util.List;

public interface UserTaskQuery {

    List<Task> getTasksForUserGroup(String group);

    List<Task> getTasksForUser(String user);

    Task getTask(String user, String taskId);
}
