package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.tasklist.application.accesscontrol.UserTaskAccessProvider;
import io.miragon.miranum.platform.tasklist.application.port.in.WorkOnUserTaskUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskCommandPort;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkOnUserTaskService implements WorkOnUserTaskUseCase {

    private final TaskOutPort taskOutPort;
    private final TaskCommandPort taskCommandPort;
    private final UserTaskAccessProvider userTaskAccessProvider;

    @Override
    public void completeUserTask(String user, String taskId, Map<String, Object> payload) throws TaskAccessDeniedException {
        final Task task = this.getTaskIfUserHasAccess(taskId, user);
        this.taskCommandPort.completeUserTask(task.getId(), payload);
    }

    @Override
    public void saveUserTask(String user, String taskId, Map<String, Object> payload) throws TaskAccessDeniedException {
        final Task task = this.getTaskIfUserHasAccess(taskId, user);
        this.taskCommandPort.saveUserTask(task.getId(), payload);
    }

    @Override
    public void assignUserTask(String user, String taskId, String assignee) throws TaskAccessDeniedException {
        if (!user.equals(assignee)) {
            throw new TaskAccessDeniedException("User " + user + " can not assign task to " + assignee + ".");
        }
        final Task task = this.getTaskIfUserHasAccess(taskId, user);
        task.setAssignee(assignee);
        this.taskCommandPort.assignUserTask(task.getId(), assignee);
        this.taskOutPort.updateTaskInfo(TaskInfo.builder()
            .id(task.getId())
            .description(task.getDescription())
            .definitionName(task.getProcessName())
            .instanceId(task.getProcessInstanceId())
            .assignee(task.getAssignee())
            .formKey(task.getFormKey())
            .build());
    }

    @Override
    public void unassignUserTask(String user, String taskId) throws TaskAccessDeniedException {
        final Task task = this.getTaskIfUserHasAccess(taskId, user);
        task.setAssignee(null);
        this.taskCommandPort.unassignUserTask(task.getId());
        this.taskOutPort.updateTaskInfo(TaskInfo.builder()
                .id(task.getId())
                .description(task.getDescription())
                .definitionName(task.getProcessName())
                .instanceId(task.getProcessInstanceId())
                .assignee(task.getAssignee())
                .formKey(task.getFormKey())
                .build());
    }

    @Override
    public void cancelUserTask(String user, String taskId) throws TaskAccessDeniedException {
        final Task task = this.getTaskIfUserHasAccess(taskId, user);
        //   if (cancellationFlagOutPort.apply(task)) {
        taskCommandPort.cancelUserTask(taskId);
        //   } else {
        //      throw new IllegalArgumentException("Task " + taskId + " can not be cancelled.");
        //  }
    }

    private Task getTaskIfUserHasAccess(final String taskId, final String user) {
        final Task task = this.taskOutPort.getTask(taskId);
        return userTaskAccessProvider.hasAccess(task, user);
    }

}
