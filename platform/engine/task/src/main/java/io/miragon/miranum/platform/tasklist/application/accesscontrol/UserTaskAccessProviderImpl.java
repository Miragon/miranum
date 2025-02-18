package io.miragon.miranum.platform.tasklist.application.accesscontrol;

import io.miragon.miranum.auth.api.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserTaskAccessProviderImpl implements UserTaskAccessProvider {

    private final UserAuthenticationProvider authenticationProvider;

    public Task hasAccess(final Task task, final String user) throws TaskAccessDeniedException {
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
