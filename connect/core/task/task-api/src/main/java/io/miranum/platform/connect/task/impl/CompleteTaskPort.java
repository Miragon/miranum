package io.miranum.platform.connect.task.impl;


import io.miranum.platform.connect.task.api.CompleteTaskCommand;

public interface CompleteTaskPort {

    void completeTask(CompleteTaskCommand command);

}
