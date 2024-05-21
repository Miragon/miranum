package io.miragon.miranum.platform.example.application.out.task;

import io.miragon.miranum.platform.example.domain.task.TaskInfo;

import java.util.List;
import java.util.Optional;

public interface TaskInfoOutPort {

    List<TaskInfo> getTaskInfos(final List<String> taskIds);

    Optional<TaskInfo> getTaskInfo(final String taskId);

    void createTaskInfo(final TaskInfo task);

    void updateTaskInfo(final TaskInfo task);

    void deleteTaskInfo(final String taskId);

}
