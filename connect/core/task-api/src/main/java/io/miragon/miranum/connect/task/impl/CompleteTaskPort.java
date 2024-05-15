package io.miragon.miranum.connect.task.impl;


import io.miragon.miranum.connect.task.api.CompleteTaskCommand;

public interface CompleteTaskPort {

    void completeTask(CompleteTaskCommand command);

}
