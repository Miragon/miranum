package io.miragon.miranum.platform.connect.task.impl;

import io.miragon.miranum.platform.connect.task.api.CompleteTaskCommand;
import io.miragon.miranum.platform.connect.task.api.TaskApi;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class TaskApiImpl implements TaskApi {

    private final CompleteTaskPort completeTaskPort;

    @Override
    public void completeTask(final CompleteTaskCommand command) {
        this.completeTaskPort.completeTask(command);
    }
}
