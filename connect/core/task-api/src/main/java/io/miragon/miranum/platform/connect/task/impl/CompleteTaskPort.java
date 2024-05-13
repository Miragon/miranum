package io.miragon.miranum.platform.connect.task.impl;


import io.miragon.miranum.platform.connect.task.api.CompleteTaskCommand;

public interface CompleteTaskPort {

    void completeTask(CompleteTaskCommand command);

}
