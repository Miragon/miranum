package io.miragon.miranum.platform.engine.adapter.in.process;

import io.miragon.miranum.auth.api.UserAuthenticationProvider;
import io.miragon.miranum.platform.engine.application.port.in.process.ProcessDefinitionQuery;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    private final UserAuthenticationProvider authenticationProvider;

    private final ProcessDefinitionApiMapper serviceDefinitionApiMapper;


    @GetMapping
    @Operation(description = "load all available service definitions")
    public List<ProcessDefinitionDto> getServiceDefinitions(
            @RequestParam(value = "query", required = false) @Nullable final String query
    ) {
        final List<MiranumProcessDefinition> definitions = this.processDefinitionQuery.getProcessDefinitions(
                this.authenticationProvider.getLoggedInUser(),
                this.authenticationProvider.getLoggedInUserRoles(),
                query
        );
        return definitions.stream().map(this.serviceDefinitionApiMapper::map2TO).toList();
    }


}
