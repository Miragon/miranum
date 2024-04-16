package io.miragon.miranum.platform.connect.task.task.impl;


import io.miragon.miranum.platform.connect.task.task.api.CompleteTaskCommand;

public interface CompleteTaskPort {

    void completeTask(CompleteTaskCommand command);

}
