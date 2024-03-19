package io.miranum.platform.engine.application.port.out.process;

import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;

import java.util.List;

public interface MiranumProcessDefinitionPort {

    MiranumProcessDefinition getProcessDefinition(String definitionKey);

    List<MiranumProcessDefinition> getServiceDefinitions();

    boolean allowedToStartDefinition(final String userId, final List<String> groupIds, final String resourceId);


}
