package io.miragon.miranum.connect.task;

import io.miragon.miranum.connect.task.api.command.AssignUserTaskCommand;
import io.miragon.miranum.connect.task.api.command.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.exception.TaskOperationFailedException;
import io.miragon.miranum.connect.task.impl.TaskOutPort;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.community.rest.impl.RemoteTaskService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class C7TaskApi implements TaskOutPort {

    private final RemoteTaskService remoteTaskService;

    @Override
    public boolean userHasAccess(final String taskId, final String user, final List<String> groups) {
        try {
            final TaskQuery query = remoteTaskService.createTaskQuery()
                    .taskId(taskId)
                    .taskAssignee(user)
                    .or()
                    .taskCandidateUser(user);
            if (groups != null) {
                query.or().taskCandidateGroupIn(groups);
            }
            final org.camunda.bpm.engine.task.Task task = query.singleResult();
            return task != null;
        } catch (final RuntimeException e) {
            return false;
        }
    }

    @Override
    public void completeTask(CompleteTaskCommand command) {
        try {
            remoteTaskService.complete(command.getTaskId(), command.getVariables());
        } catch (final RuntimeException e) {
            throw new TaskOperationFailedException(e.getMessage());
        }
    }

    @Override
    public void assignUserTask(AssignUserTaskCommand command) {
        try {
            remoteTaskService.setAssignee(command.getTaskId(), command.getAssignee());
        } catch (final RuntimeException e) {
            throw new TaskOperationFailedException(e.getMessage());
        }
    }

    @Override
    public void cancelUserTask(String taskId) {
        try {
            remoteTaskService.deleteTask(taskId);
        } catch (final RuntimeException e) {
            throw new TaskOperationFailedException(e.getMessage());
        }
    }
}
