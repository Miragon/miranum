package io.miranum.platform.tasklist.application.service;


import io.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miranum.platform.tasklist.application.port.out.cancellation.CancellationFlagOutPort;
import io.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miranum.platform.tasklist.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserTaskQueryImpl implements UserTaskQuery {

    private final TaskOutPort taskOutPort;
    private final CancellationFlagOutPort cancellationFlagOutPort;


    @Override
    public List<Task> getTasksForUserGroup(String group) {
        return null;
    }

    @Override
    public List<Task> getTasksForUser(String user) {
        return null;
    }

    @Override
    public Task getTask(String user, String taskId) {
        return null;
    }


}
