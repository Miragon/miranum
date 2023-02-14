package io.miragon.miranum.connect.task.impl;

import io.miragon.miranum.connect.task.api.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.TaskApi;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class TaskService implements TaskApi {

    private final CompleteTaskPort completeTaskPort;

    @Override
    public void completeTask(final CompleteTaskCommand command) {
        this.completeTaskPort.completeTask(command);
    }
}
