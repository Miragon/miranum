package io.miragon.miranum.platform.tasklist.application.accesscontrol;

import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;

public interface UserTaskAccessProvider {

    Task hasAccess(final Task task, final String user) throws TaskAccessDeniedException;

}
