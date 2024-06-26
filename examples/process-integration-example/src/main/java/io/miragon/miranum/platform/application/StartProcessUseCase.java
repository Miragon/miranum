package io.miragon.miranum.platform.application;

import io.miragon.miranum.connect.process.api.ProcessApi;
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import io.miragon.miranum.platform.application.port.in.StartProcessInstanceCommand;
import io.miragon.miranum.platform.application.port.in.StartProcessInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StartProcessUseCase implements StartProcessInstance {
    private final ProcessApi processApi;

    @Override
    public void startInstance(StartProcessInstanceCommand command) {
        this.processApi.startProcess(
                StartProcessCommand.builder()
                     .processKey(command.definitionKey())
                     .correlationKey(command.correlationKey())
                     .variables(command.variables())
                     .build()
        );
    }

}
