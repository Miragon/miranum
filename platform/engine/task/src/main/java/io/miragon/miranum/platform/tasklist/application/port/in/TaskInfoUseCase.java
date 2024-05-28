package io.miragon.miranum.platform.tasklist.application.port.in;

import org.camunda.bpm.engine.delegate.DelegateTask;

public interface TaskInfoUseCase {

    void createTask(final DelegateTask task);

    void assignTask(final String taskId, final String assignee);

    void deleteTask(final String taskId);

}
