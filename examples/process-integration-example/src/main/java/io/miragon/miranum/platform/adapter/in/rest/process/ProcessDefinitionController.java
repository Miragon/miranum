package io.miragon.miranum.platform.adapter.in.rest.process;

import io.miragon.miranum.platform.application.port.in.StartProcessInstanceInPort;
import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API to interact with process definitions.
 */
@RestController
@RequestMapping("/rest/process/definition")
@RequiredArgsConstructor
@Tag(name = "ProcessDefinitionController", description = "API to interact with service definitions")
public class ProcessDefinitionController {

    private final StartProcessInstanceInPort startProcessInstanceInPort;
    private final UserAuthenticationProvider authenticationProvider;

    @PostMapping()
    @Operation(description = "Start a specific service")
    public ResponseEntity<Void> startInstance(@RequestBody final StartInstanceDto to) {
        this.startProcessInstanceInPort.startInstance(to.getKey(), to.getVariables(), this.authenticationProvider.getLoggedInUser(),
                this.authenticationProvider.getLoggedInUserRoles());
        return ResponseEntity.ok().build();
    }


}
