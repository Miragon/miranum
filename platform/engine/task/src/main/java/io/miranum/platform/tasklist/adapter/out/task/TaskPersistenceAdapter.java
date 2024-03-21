package io.miranum.platform.tasklist.adapter.out.task;

import io.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miranum.platform.tasklist.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements TaskOutPort {
    @Override
    public List<Task> getTasksForUser(String user) {
        return null;
    }

    @Override
    public List<Task> getTasksForUserGroup(String user, String group) {
        return null;
    }

    @Override
    public Task getTask(String user, String taskId) {
        return null;
    }

    @Override
    public Map<String, Object> getTaskData(String user, String taskId) {
        return null;
    }
}
