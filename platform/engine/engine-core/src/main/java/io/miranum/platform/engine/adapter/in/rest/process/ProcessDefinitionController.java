package io.miranum.platform.engine.adapter.in.rest.process;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miranum.platform.engine.application.port.in.process.ProcessDefinitionQuery;
import io.miranum.platform.engine.application.port.in.process.StartProcessInstanceUseCase;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    private final AppAuthenticationProvider authenticationProvider;

    private final ProcessDefinitionApiMapper serviceDefinitionApiMapper;


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

    @PostMapping()
    @Operation(description = "Start a specific service")
    public ResponseEntity<Void> startInstance(@RequestBody final StartInstanceDto to) {
        this.startProcessInstanceUseCase.startInstance(to.getKey(), to.getVariables(), this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok().build();
    }


}
