package io.miragon.miranum.platform.tasklist.adapter.out.task.miranum;

import io.miragon.miranum.platform.tasklist.application.port.out.task.AssignTaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.AssignUserTask;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MiranumTaskAdapter implements AssignTaskOutPort {

    private final TaskService taskService;

    @Override
    public void assignUserTask(final AssignUserTask assignUserTask, final String user, final List<String> userGroups) {
        taskService.claim(assignUserTask.taskId(), assignUserTask.assignee());
    }

    @Override
    public void unassignUserTask(String taskId, String user, List<String> userGroups) {
        assignUserTask(AssignUserTask.builder()
                .taskId(taskId)
                .assignee(null)
                .build(), user, userGroups);
    }
}
