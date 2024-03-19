package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.adapter.in.engine.ProcessConstants;
import io.miranum.platform.engine.application.port.in.process.StartProcessInstanceUseCase;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessInstancePort;
import io.miranum.platform.engine.application.port.out.process.StartProcessInstancePort;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class StartProcessInstanceService implements StartProcessInstanceUseCase {

    private final MiranumProcessDefinitionPort miranumProcessDefinitionPort;
    private final StartProcessInstancePort startProcessInstancePort;
    private final MiranumProcessInstancePort miranumProcessInstancePort;

    @Override
    public void startInstance(final String definitionKey, final Map<String, Object> variables, final String userId, final List<String> groups) {
        if (!this.miranumProcessDefinitionPort.allowedToStartDefinition(userId, groups, definitionKey)) {
            throw new RuntimeException(String.format("Service definition not accessible: %s", definitionKey));
        }

        final MiranumProcessDefinition processDefinition = this.miranumProcessDefinitionPort.getProcessDefinition(definitionKey);

        //add other serializer
        variables.put(ProcessConstants.PROCESS_STARTER_OF_INSTANCE, userId);
        variables.put(ProcessConstants.PROCESS_STATUS, "started");

        final MiranumProcessInstance processInstance = this.startProcessInstancePort.startProcessInstance(processDefinition.getName(), processDefinition.getKey(), variables);

        this.miranumProcessInstancePort.save(processInstance);
        this.miranumProcessInstancePort.authorizeServiceInstance(processInstance.getId(), userId);

        log.info("process instance for key {} started: {}", processDefinition.getKey(), processInstance.getId());
    }


}
