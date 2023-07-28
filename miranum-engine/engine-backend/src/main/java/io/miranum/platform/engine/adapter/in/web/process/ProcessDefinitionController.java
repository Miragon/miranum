package io.miranum.platform.engine.adapter.in.web.process;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miranum.platform.engine.application.port.in.process.InitializeStartContextUseCase;
import io.miranum.platform.engine.application.port.in.process.ProcessDefinitionQuery;
import io.miranum.platform.engine.application.port.in.process.StartProcessInstanceUseCase;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinitionWithSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest API to interact with process definitions.
 */
@RestController
@Transactional
@RequestMapping("/rest/process/definition")
@RequiredArgsConstructor
@Tag(name = "ServiceDefinitionController", description = "API to interact with service definitions")
public class ProcessDefinitionController {

    private final ProcessDefinitionQuery processDefinitionQuery;
    private final StartProcessInstanceUseCase startProcessInstanceUseCase;
    private final InitializeStartContextUseCase initializeStartContextUseCase;

    private final AppAuthenticationProvider authenticationProvider;

    //Mapper
    private final ProcessDefinitionApiMapper serviceDefinitionApiMapper;

    /**
     * Get all service definition.
     *
     * @return all service definitions
     */
    @GetMapping
    @Operation(description = "load all available service definitions")
    public ResponseEntity<List<ProcessDefinitionDto>> getServiceDefinitions() {
        final List<MiranumProcessDefinition> definitions = this.processDefinitionQuery.getProcessDefinitions(
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups()
        );
        return ResponseEntity.ok(this.serviceDefinitionApiMapper.map2TO(definitions));
    }

    /**
     * Get a detail object of the service definition.
     *
     * @param key key of the service
     * @return service definition
     */
    @GetMapping("/{key}")
    @Operation(description = "Get a specific service definition")
    public ResponseEntity<ProcessDefinitionWithSchemaDto> getServiceDefinition(@PathVariable("key") final String key) {
        final MiranumProcessDefinitionWithSchema definition = this.processDefinitionQuery
                .getProcessDefinitionWithSchema(this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups(), key);
        return ResponseEntity.ok(this.serviceDefinitionApiMapper.map2TO(definition));
    }

    /**
     * Start the specified service.
     *
     * @param to data to start a service
     */
    @PostMapping()
    @Operation(description = "Start a specific service")
    public ResponseEntity<Void> startInstance(@RequestBody final StartInstanceDto to) {
        this.startProcessInstanceUseCase.startInstance(to.getKey(), to.getVariables(), this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/context/{key}")
    @Operation(description = "Initialize the start context of a specific process")
    public ResponseEntity<Void> initalizeStartContext(@PathVariable("key") final String key) {
        this.initializeStartContextUseCase.initialize(key, this.authenticationProvider.getCurrentUserId());
        return ResponseEntity.ok().build();
    }

}
