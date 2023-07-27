package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.process.ProcessDefinitionQuery;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinitionWithSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessDefinitionQueryService implements ProcessDefinitionQuery {

    private final MiranumProcessDefinitionPort miranumProcessDefinitionPort;

    @Override
    public List<MiranumProcessDefinition> getProcessDefinitions(String userId, List<String> groups) {
        final List<MiranumProcessDefinition> processDefinitions = miranumProcessDefinitionPort.getServiceDefinitions();

        return processDefinitions.stream()
                .filter(obj -> miranumProcessDefinitionPort.allowedToStartDefinition(userId, groups, obj.getKey()))
                .toList();
    }

    @Override
    public MiranumProcessDefinitionWithSchema getProcessDefinitionWithSchema(String userId, List<String> groups, String key) {
        return null;
    }
}
