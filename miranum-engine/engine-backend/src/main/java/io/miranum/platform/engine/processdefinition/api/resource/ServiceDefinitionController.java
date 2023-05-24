package io.miranum.platform.engine.processdefinition.api.resource;

import io.miranum.platform.engine.api.AppAuthenticationProvider;
import io.miranum.platform.engine.processdefinition.api.mapper.ServiceDefinitionApiMapper;
import io.miranum.platform.engine.processdefinition.api.transport.ServiceDefinitionDetailTO;
import io.miranum.platform.engine.processdefinition.api.transport.ServiceDefinitionTO;
import io.miranum.platform.engine.processdefinition.api.transport.StartInstanceTO;
import io.miranum.platform.engine.processdefinition.domain.facade.ServiceDefinitionFacade;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinition;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinitionDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest API to interact with service definitions.
 */
@RestController
@Transactional
@RequestMapping("/rest/service/definition")
@RequiredArgsConstructor
@Tag(name = "ServiceDefinitionController", description = "API to interact with service definitions")
public class ServiceDefinitionController {

    private final ServiceDefinitionFacade serviceDefinitionFacade;
    private final AppAuthenticationProvider authenticationProvider;

    //Mapper
    private final ServiceDefinitionApiMapper serviceDefinitionApiMapper;

    /**
     * Get all service definition.
     *
     * @return all service definitions
     */
    @GetMapping
    @Operation(description = "load all available service definitions")
    public ResponseEntity<List<ServiceDefinitionTO>> getServiceDefinitions() {
        final List<ServiceDefinition> definitions = this.serviceDefinitionFacade.getStartableServiceDefinitions(
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
    public ResponseEntity<ServiceDefinitionDetailTO> getServiceDefinition(@PathVariable("key") final String key) {
        final ServiceDefinitionDetail definition = this.serviceDefinitionFacade
                .getServiceDefinitionDetail(key, this.authenticationProvider.getCurrentUserId(), this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok(this.serviceDefinitionApiMapper.map2TO(definition));
    }

    /**
     * Start the specified service.
     *
     * @param to data to start a service
     */
    @PostMapping()
    @Operation(description = "Start a specific service")
    public ResponseEntity<Void> startInstance(@RequestBody final StartInstanceTO to) {
        this.serviceDefinitionFacade.startInstance(to.getKey(), to.getVariables(), this.authenticationProvider.getCurrentUserId(),
                this.authenticationProvider.getCurrentUserGroups());
        return ResponseEntity.ok().build();
    }

}
