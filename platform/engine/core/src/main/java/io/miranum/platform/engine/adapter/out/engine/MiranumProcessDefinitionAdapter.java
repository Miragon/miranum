package io.miranum.platform.engine.adapter.out.engine;

import io.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
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
public class MiranumProcessDefinitionAdapter implements MiranumProcessDefinitionPort {

    private final RepositoryService repositoryService;
    private final AuthorizationService authorizationService;
    private final MiranumProcessDefinitionMapper miranumProcessDefinitionMapper;

    @Override
    public MiranumProcessDefinition getProcessDefinition(String definitionKey) {
        final ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .processDefinitionKey(definitionKey)
                .singleResult();
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
