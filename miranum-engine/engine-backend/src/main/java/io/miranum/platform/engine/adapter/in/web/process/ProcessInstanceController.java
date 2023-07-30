package io.miranum.platform.engine.adapter.in.web.process;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miranum.platform.engine.application.port.in.process.MiranumProcessInstanceQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private final MiranumProcessInstanceQuery instanceQuery;
    private final AppAuthenticationProvider authenticationProvider;

    //Mapper
    private final ProcessInstanceApiMapper serviceInstanceApiMapper;

    /**
     * Get all assigned process instances.
     *
     * @return assigned process instances
     */
    @GetMapping()
    public ResponseEntity<List<ProcessInstanceDto>> getAssignedInstances() {
        val startedInstances = this.instanceQuery.getProcessInstanceByUser(this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok(this.serviceInstanceApiMapper.map2TO(startedInstances));
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
