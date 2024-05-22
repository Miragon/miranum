package io.miragon.miranum.platform.tasklist.application.port.out.engine;

import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface TaskOutPort {

    List<Task> getTasksForUser(String user);


    List<Task> getTasksForUserGroup(String user, String group);


    Task getTask(String user, String taskId);

    Map<String, Object> getTaskData(String user, String taskId);

    Optional<TaskInfo> getTaskInfo(final String taskId);

    void createTaskInfo(final TaskInfo task);

    void updateTaskInfo(final TaskInfo task);

    void deleteTaskInfo(final String taskId);

}
