package io.miragon.miranum.platform.tasklist.application.port.out.task;

import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import io.miragon.miranum.platform.tasklist.exception.TaskNotFoundException;

import java.util.List;
import java.util.Map;


public interface TaskOutPort {

    List<Task> getTasksForUser(String user);


    List<Task> getTasksForUserGroup(String user, String group);

    Task getTask(String taskId) throws TaskNotFoundException;

    Map<String, Object> getTaskData(String user, String taskId);

    void createTask(final TaskInfo task);

    void assignTask(final String taskId, final String assignee);

    void deleteTask(final String taskId);

}
