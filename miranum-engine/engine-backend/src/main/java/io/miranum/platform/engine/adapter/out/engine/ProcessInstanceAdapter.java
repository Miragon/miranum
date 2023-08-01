package io.miranum.platform.engine.adapter.out.engine;

import io.miranum.platform.engine.application.port.out.process.StartProcessInstancePort;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProcessInstanceAdapter implements StartProcessInstancePort {

    private final RuntimeService runtimeService;

    @Override
    public MiranumProcessInstance startProcessInstance(
            final String definitionName,
            final String definitionKey,
            final Map<String, Object> variables) {
        final ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey(definitionKey, variables);
        return new MiranumProcessInstance(definitionKey, definitionName, processInstance.getProcessInstanceId());
    }
}
