package io.miragon.miranum.platform.engine.adapter.out.engine;

import io.miragon.miranum.platform.engine.adapter.out.engine.mapper.MiranumProcessDefinitionMapper;
import io.miragon.miranum.platform.engine.application.port.out.process.ProcessDefinitionOutPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MiranumProcessDefinitionAdapter implements ProcessDefinitionOutPort {

    private final RepositoryService repositoryService;
    private final AuthorizationService authorizationService;
    private final MiranumProcessDefinitionMapper miranumProcessDefinitionMapper;

    @Override
    public MiranumProcessDefinition getProcessDefinition(final String definitionKey) {
        final ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .processDefinitionKey(definitionKey)
                .singleResult();
        return this.miranumProcessDefinitionMapper.map(processDefinition);

    }

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


    @Override
    public List<MiranumProcessDefinition> getServiceDefinitions() {
        final List<ProcessDefinition> serviceDefinitions = this.repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .list();
        return this.miranumProcessDefinitionMapper.map(serviceDefinitions);
    }

    @Override
    public boolean allowedToStartDefinition(final String userId, final List<String> groupIds, final String resourceId) {

        return this.authorizationService.isUserAuthorized(
                userId,
                groupIds,
                Permissions.CREATE_INSTANCE,
                Resources.PROCESS_DEFINITION,
                resourceId);
    }

}
