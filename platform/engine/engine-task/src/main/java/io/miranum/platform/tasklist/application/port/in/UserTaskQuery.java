package io.miranum.platform.tasklist.application.port.in;


import io.miranum.platform.tasklist.domain.Task;
import io.miranum.platform.tasklist.domain.TaskWithSchema;

import java.util.List;

public interface UserTaskQuery {

    List<Task> getTasksForUserGroup(String group);

    List<Task> getTasksForUser(String user);

    TaskWithSchema getTask(String user, String taskId);
}
