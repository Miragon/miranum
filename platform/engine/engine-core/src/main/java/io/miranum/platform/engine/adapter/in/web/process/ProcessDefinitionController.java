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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Rest API to interact with process definitions.
 */
@RestController
@Transactional
@RequestMapping("/rest/process/definition")
@RequiredArgsConstructor
@Tag(name = "ProcessDefinitionController", description = "API to interact with service definitions")
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
    public Page<ProcessDefinitionDto> getServiceDefinitions(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) final int page,
            @RequestParam(value = "size", defaultValue = "50", required = false) @Min(1) @Max(50) final int size,
            @RequestParam(value = "query", required = false) @Nullable final String query
    ) {
        final Page<MiranumProcessDefinition> definitions = this.processDefinitionQuery.getProcessDefinitions(
                this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups(),
                page,
                size,
                query

        );
        return definitions.map(this.serviceDefinitionApiMapper::map2TO);
    }

    /**
     * Get a detail object of the service definition.
     *
     * @param key key of the service
     * @return service definition
     */
    @GetMapping("/{key}")
    @Operation(description = "Get a specific service definition")
    public ResponseEntity<ProcessDefinitionWithSchemaDto> getProcessDefinition(@PathVariable("key") final String key) {
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
