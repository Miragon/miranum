package io.miragon.miranum.platform.example.application.in.task;

import io.miragon.miranum.platform.example.domain.task.TaskInfo;
import org.camunda.bpm.engine.delegate.DelegateTask;

import java.util.List;

public interface TaskInfoInPort {

    TaskInfo getTaskInfo(final String taskId);

    List<TaskInfo> getTaskInfos(final List<String> taskIds);

    void createTaskInfo(final DelegateTask task);

    void updateTaskInfo(final String taskId, final DelegateTask task);

    void deleteTaskInfo(final String taskId);

}
