package io.miranum.platform.tasklist.application.service;


import io.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miranum.platform.tasklist.application.port.out.cancellation.CancellationFlagOutPort;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskOutPort;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaRefResolverPort;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaTypeResolverPort;
import io.miranum.platform.tasklist.domain.Task;
import io.miranum.platform.tasklist.domain.TaskWithSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserTaskQueryImpl implements UserTaskQuery {

    private final TaskOutPort taskOutPort;
    private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;
    private final CancellationFlagOutPort cancellationFlagOutPort;
    private final TaskSchemaTypeResolverPort taskSchemaTypeResolverPort;


    @Override
    public List<Task> getTasksForUserGroup(String group) {
        return null;
    }

    @Override
    public List<Task> getTasksForUser(String user) {
        return null;
    }

    @Override
    public TaskWithSchema getTask(String user, String taskId) {
        return null;
    }


    private TaskWithSchema enrichWithSchema(Task result) {
        return null;
    }

}
