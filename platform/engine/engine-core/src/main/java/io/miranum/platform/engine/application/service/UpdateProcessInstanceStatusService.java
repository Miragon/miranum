package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.process.UpdateProcessInstanceStatusUseCase;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessInstancePort;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProcessInstanceStatusService implements UpdateProcessInstanceStatusUseCase {

    private final MiranumProcessInstancePort miranumProcessInstancePort;

    @Override
    public void updateStatus(String processInstanceId, String statusKey, String statusName) {
        final MiranumProcessInstance instance = miranumProcessInstancePort.searchProcessInstanceById(processInstanceId).orElseThrow();
        instance.updateStatus(statusKey, statusName);
        miranumProcessInstancePort.save(instance);
    }
}
