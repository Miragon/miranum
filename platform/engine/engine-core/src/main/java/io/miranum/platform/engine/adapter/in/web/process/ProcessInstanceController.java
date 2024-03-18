package io.miranum.platform.engine.adapter.in.web.process;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miranum.platform.engine.application.port.in.process.ProcessInstanceQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Rest API to interact with process instances.
 */
@RestController
@Transactional
@RequestMapping("/rest/process/instance")
@RequiredArgsConstructor
@Tag(name = "ProcessInstanceController", description = "API to interact with process instances")
public class ProcessInstanceController {

    private final ProcessInstanceQuery instanceQuery;
    private final AppAuthenticationProvider authenticationProvider;

    //Mapper
    private final ProcessInstanceApiMapper serviceInstanceApiMapper;

    /**
     * Get all assigned process instances.
     *
     * @return assigned process instances
     */
    @GetMapping()
    public Page<ProcessInstanceDto> getAssignedInstances(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) final int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) @Min(1) @Max(50) final int size,
            @RequestParam(value = "query", required = false) @Nullable final String query
    ) {
        val userId = this.authenticationProvider.getCurrentUserId();
        val startedInstances = this.instanceQuery.getProcessInstanceByUser(userId, page, size, query);
        return startedInstances.map(this.serviceInstanceApiMapper::map2TO);
    }

    /**
     * Get detail representation of a process instance.
     *
     * @param id Id of the process instance
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProcessInstanceDetailDto> getProcessInstanceDetail(@PathVariable("id") final String id) {
        val startedInstances = this.instanceQuery.getServiceInstanceWithDataByUser(this.authenticationProvider.getCurrentUserId(), id);
        return ResponseEntity.ok(this.serviceInstanceApiMapper.map2TO(startedInstances));
    }

}
