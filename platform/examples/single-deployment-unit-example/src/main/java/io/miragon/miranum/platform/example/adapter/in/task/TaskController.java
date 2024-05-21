package io.miragon.miranum.platform.example.adapter.in.task;

import io.miragon.miranum.platform.example.adapter.in.task.dto.AssignTaskDto;
import io.miragon.miranum.platform.example.adapter.in.task.dto.CompleteTaskDto;
import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.application.port.in.UserTaskQuery;
import io.miragon.miranum.platform.tasklist.application.port.in.WorkOnUserTaskUseCase;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/task")
@RequiredArgsConstructor
@Tag(name = "TaskController", description = "API for Tasks from the task list")
public class TaskController {

    private final UserTaskQuery userTaskQuery;
    private final WorkOnUserTaskUseCase workOnUserTaskUseCase;
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

    @PostMapping("/{taskId}/complete")
    public void completeTask(
            @Parameter(name = "taskId", description = "A task id string used during search with the task string.", in = ParameterIn.PATH) @Valid @PathVariable(value = "taskId", required = false) String taskId,
            @Valid @RequestBody CompleteTaskDto completeTaskDto
    ) {
        this.workOnUserTaskUseCase.completeUserTask(authenticationProvider.getLoggedInUser(), taskId, completeTaskDto.getPayload());
    }

    @PostMapping("/{taskId}/assign")
    public void claimTask(
            @Parameter(name = "taskId", description = "A task id string used during search with the task string.", in = ParameterIn.PATH) @Valid @PathVariable(value = "taskId", required = false) String taskId,
            @Valid @RequestBody AssignTaskDto assignTaskDto
            ) {
        this.workOnUserTaskUseCase.assignUserTask(authenticationProvider.getLoggedInUser(), taskId, assignTaskDto.getAssignee());
    }

    @PostMapping("/{taskId}/unassign")
    public void unassignTask(
            @Parameter(name = "taskId", description = "A task id string used during search with the task string.", in = ParameterIn.PATH) @Valid @PathVariable(value = "taskId", required = false) String taskId
    ) {
        this.workOnUserTaskUseCase.unassignUserTask(authenticationProvider.getLoggedInUser(), taskId);
    }

}
