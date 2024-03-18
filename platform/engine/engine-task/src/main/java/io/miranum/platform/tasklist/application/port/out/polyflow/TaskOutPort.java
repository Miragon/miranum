package io.miranum.platform.tasklist.application.port.out.polyflow;

import io.miranum.platform.tasklist.domain.Task;
import io.miranum.platform.user.domain.User;

import java.util.List;
import java.util.Map;


public interface TaskOutPort {

    List<Task> getTasksForUser(String user);


    List<Task> getTasksForUserGroup(String user, String group);


    Task getTask(User user, String taskId);

    Map<String, Object> getTaskData(User user, String taskId);
}
