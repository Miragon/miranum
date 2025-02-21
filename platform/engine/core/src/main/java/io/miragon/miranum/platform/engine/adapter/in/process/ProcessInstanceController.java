package io.miragon.miranum.platform.engine.adapter.in.process;

import io.miragon.miranum.auth.api.UserAuthenticationProvider;
import io.miragon.miranum.platform.engine.adapter.in.process.dto.ProcessInstanceDto;
import io.miragon.miranum.platform.engine.adapter.in.process.mapper.ProcessInstanceApiMapper;
import io.miragon.miranum.platform.engine.application.port.in.process.ProcessInstanceInPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest API to interact with process instances.
 */
@RestController
@Transactional
@RequestMapping("/rest/process/instance")
@RequiredArgsConstructor
@Tag(name = "ProcessInstanceController", description = "API to interact with process instances")
public class ProcessInstanceController {

    private final ProcessInstanceInPort instanceQuery;
    private final UserAuthenticationProvider authenticationProvider;

    //Mapper
    private final ProcessInstanceApiMapper serviceInstanceApiMapper;

    /**
     * Get all assigned process instances.
     *
     * @return assigned process instances
     */
    @GetMapping()
    public List<ProcessInstanceDto> getAssignedInstances(
            @RequestParam(value = "query", required = false) @Nullable final String query
    ) {
        val userId = this.authenticationProvider.getLoggedInUser();
        val startedInstances = this.instanceQuery.getProcessInstanceByUser(userId, query);
        return startedInstances.stream().map(this.serviceInstanceApiMapper::map2TO).toList();
    }


}
