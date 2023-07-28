package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.process.UpdateProcessInstanceDescriptionUseCase;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessInstancePort;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProcessInstanceDescriptionService implements UpdateProcessInstanceDescriptionUseCase {

    private final MiranumProcessInstancePort miranumProcessInstancePort;

    @Override
    public void updateDescription(String processInstanceId, String description) {
        final MiranumProcessInstance instance = miranumProcessInstancePort.searchProcessInstanceById(processInstanceId).orElseThrow();
        instance.updateDescription(description);
        miranumProcessInstancePort.save(instance);
    }
}
