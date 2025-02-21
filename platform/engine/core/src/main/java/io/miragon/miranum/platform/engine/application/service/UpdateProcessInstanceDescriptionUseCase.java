package io.miragon.miranum.platform.engine.application.service;

import io.miragon.miranum.platform.engine.application.port.out.process.ProcessInstanceOutPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProcessInstanceDescriptionUseCase implements io.miragon.miranum.platform.engine.application.port.in.process.UpdateProcessInstanceDescriptionUseCase {

    private final ProcessInstanceOutPort miranumProcessInstancePort;

    @Override
    public void updateDescription(String processInstanceId, String description) {
        final MiranumProcessInstance instance = miranumProcessInstancePort.searchProcessInstanceById(processInstanceId).orElseThrow();
        instance.updateDescription(description);
        miranumProcessInstancePort.save(instance);
    }
}
