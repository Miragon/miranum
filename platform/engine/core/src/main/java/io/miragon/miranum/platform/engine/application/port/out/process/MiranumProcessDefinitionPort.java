package io.miragon.miranum.platform.engine.application.port.out.process;

import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;

import java.util.List;

public interface MiranumProcessDefinitionPort {

    MiranumProcessDefinition getProcessDefinition(final String definitionKey);

    MiranumProcessDefinition getProcessDefinitionById(final String definitionId);

    List<MiranumProcessDefinition> getServiceDefinitions();

    boolean allowedToStartDefinition(final String userId, final List<String> groupIds, final String resourceId);


}
