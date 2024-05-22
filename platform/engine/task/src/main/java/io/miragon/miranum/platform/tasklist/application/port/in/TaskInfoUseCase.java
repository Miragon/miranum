package io.miragon.miranum.platform.tasklist.application.port.in;

import org.camunda.bpm.engine.delegate.DelegateTask;

public interface TaskInfoUseCase {

    void createTaskInfo(final DelegateTask task);

    void deleteTaskInfo(final String taskId);

}
