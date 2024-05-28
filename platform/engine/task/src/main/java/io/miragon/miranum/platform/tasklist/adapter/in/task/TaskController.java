package io.miragon.miranum.platform.tasklist.adapter.in.task;

import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/task")
@RequiredArgsConstructor
@Tag(name = "TaskController", description = "API for Tasks from the task list")
@ConditionalOnProperty(value = "miranum.tasklist.enabled", havingValue = "true", matchIfMissing = true)
public class TaskController {

    private final UserTaskQuery userTaskQuery;
    private final UserAuthenticationProvider authenticationProvider;

    @GetMapping("/user")
    public List<Task> getUserTasksForCurrentUser() {
        return userTaskQuery.getTasksForUser(authenticationProvider.getLoggedInUser());
    }

    @GetMapping("/group/{group}")
    public List<Task> getTasksForGroup(
            @Parameter(name = "group", description = "A group string used during search with the group string.", in = ParameterIn.PATH) @NotBlank @PathVariable(value = "group", required = false) String group
    ) {
        return userTaskQuery.getTasksForUserGroup(authenticationProvider.getLoggedInUser(), group);
    }

    @GetMapping("/{taskId}")
    public Task getTask(
            @Parameter(name = "taskId", description = "A task id string used during search with the task string.", in = ParameterIn.PATH) @Valid @PathVariable(value = "taskId", required = false) String taskId
    ) {
        return userTaskQuery.getTask(taskId, authenticationProvider.getLoggedInUser());
    }

}
