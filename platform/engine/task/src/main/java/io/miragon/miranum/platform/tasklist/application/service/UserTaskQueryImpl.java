package io.miragon.miranum.platform.tasklist.application.service;


import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserTaskQueryImpl implements UserTaskQuery {

    private final TaskOutPort taskOutPort;
    private final UserAuthenticationProvider authenticationProvider;


    @Override
    public List<Task> getTasksForUserGroup(final String group, final String user) {
        return this.taskOutPort.getTasksForUserGroup(group, user);
    }

    @Override
    public List<Task> getTasksForUser(final String user) {
        return this.taskOutPort.getTasksForUser(user);
    }

    @Override
    public Task getTask(final String user, final String taskId) throws TaskAccessDeniedException {
        return this.hasAccess(taskId, user);
    }

    private Task hasAccess(final String taskId, final String user) throws TaskAccessDeniedException {
        final Task task = this.taskOutPort.getTask(taskId);
        final boolean userInCandidateGroup = authenticationProvider.getLoggedInUserRoles().stream()
                .anyMatch(role -> task.getCandidateGroups().contains(role));
        if ( (task.getAssignee() != null && task.getAssignee().equals(user))
                || (task.getCandidateUsers() != null && task.getCandidateUsers().contains(user))
                || userInCandidateGroup) {
            return task;
        }
        throw new TaskAccessDeniedException("User " + user + " has no access to task " + task.getId());
    }

}
