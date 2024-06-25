package io.miragon.miranum.platform.adapter.in.rest.task;

import io.miragon.miranum.platform.adapter.in.rest.task.dto.CompleteTaskDto;
import io.miragon.miranum.platform.application.port.in.WorkOnUserTaskInPort;
import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/task")
@RequiredArgsConstructor
@Tag(name = "TaskController", description = "API for Tasks from the task list")
public class TaskController {

    private final WorkOnUserTaskInPort workOnUserTaskInPort;
    private final UserAuthenticationProvider authenticationProvider;

    @PostMapping("/{taskId}/complete")
    public void completeTask(
            @Parameter(name = "taskId", description = "A task id string used during search with the task string.", in = ParameterIn.PATH) @Valid @PathVariable(value = "taskId", required = false) String taskId,
            @Valid @RequestBody CompleteTaskDto completeTaskDto
    ) {
        this.workOnUserTaskInPort.completeUserTask(authenticationProvider.getLoggedInUser(), taskId, completeTaskDto.getPayload());
    }

}
