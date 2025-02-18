package io.miragon.miranum.platform.tasklist.application.port.in;

import org.camunda.bpm.engine.delegate.DelegateTask;

public interface TaskNotificationUseCase {

    void notifyUsers(final DelegateTask delegateTask);

}
