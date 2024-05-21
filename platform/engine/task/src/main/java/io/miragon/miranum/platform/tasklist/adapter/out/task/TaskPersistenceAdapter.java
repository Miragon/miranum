package io.miragon.miranum.platform.tasklist.adapter.out.task;

import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Map;

//@Component
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements TaskOutPort {
    @Override
    public List<Task> getTasksForUser(String user) {
        // TODO load tasks from camunda and map it with taskinfo table
        return null;
    }

    @Override
    public List<Task> getTasksForUserGroup(String user, String group) {
        // TODO load tasks from camunda and map it with taskinfo table
        return null;
    }

    @Override
    public Task getTask(String user, String taskId) {
        // TODO load tasks from camunda and map it with taskinfo table
        return null;
    }

    @Override
    public Map<String, Object> getTaskData(String user, String taskId) {
        throw new NotImplementedException("Not implemented yet");
    }
}
