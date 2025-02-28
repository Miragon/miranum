package io.miragon.miranum.platform.engine.impl;

import io.miragon.miranum.platform.engine.api.MiranumProcessDefinition;
import io.miragon.miranum.platform.engine.api.MiranumProcessDefinitionApi;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MiranumProcessDefinitionApiImpl implements MiranumProcessDefinitionApi {

    private final RepositoryService repositoryService;
    private final MiranumProcessDefinitionMapper miranumProcessDefinitionMapper;

    @Override
    public MiranumProcessDefinition getProcessDefinitionById(final String definitionId) {
        final ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(definitionId)
                .singleResult();
        if (processDefinition == null) {
            throw new IllegalArgumentException(String.format("The servicedefinition with the id %s is not available.", definitionId));
        }
        return this.miranumProcessDefinitionMapper.map(processDefinition);
    }

}
