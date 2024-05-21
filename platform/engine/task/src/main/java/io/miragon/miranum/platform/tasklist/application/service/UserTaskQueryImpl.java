package io.miragon.miranum.platform.tasklist.application.service;


import io.miragon.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserTaskQueryImpl implements UserTaskQuery {

    private final TaskOutPort taskOutPort;


    @Override
    public List<Task> getTasksForUserGroup(final String group, final String user) {
        return this.taskOutPort.getTasksForUserGroup(group, user);
    }

    @Override
    public List<Task> getTasksForUser(final String user) {
        return this.taskOutPort.getTasksForUser(user);
    }

    @Override
    public Task getTask(final String user, final String taskId) {
        return this.taskOutPort.getTask(user, taskId);
    }


}
