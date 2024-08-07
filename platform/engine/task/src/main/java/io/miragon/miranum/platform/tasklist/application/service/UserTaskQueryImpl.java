package io.miragon.miranum.platform.tasklist.application.service;


import io.miragon.miranum.platform.tasklist.application.accesscontrol.UserTaskAccessProvider;
import io.miragon.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserTaskQueryImpl implements UserTaskQuery {

    private final TaskOutPort taskOutPort;
    private final UserTaskAccessProvider userTaskAccessProvider;


    @Override
    public List<Task> getTasksForUserGroup(final String user, final String group) {
        return this.taskOutPort.getTasksForUserGroup(user, group);
    }

    @Override
    public List<Task> getTasksForUser(final String user) {
        return this.taskOutPort.getTasksForUser(user);
    }

    @Override
    public Task getTask(final String taskId, final String user) throws TaskAccessDeniedException {
        final Task task = this.taskOutPort.getTask(taskId);
        return userTaskAccessProvider.hasAccess(task, user);
    }

}
