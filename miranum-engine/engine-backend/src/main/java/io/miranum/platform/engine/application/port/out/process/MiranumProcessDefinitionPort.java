package io.miranum.platform.engine.application.port.out.process;

import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinitionWithSchema;

import java.util.List;

public interface MiranumProcessDefinitionPort {

    MiranumProcessDefinition getProcessDefinition(String definitionKey);

    MiranumProcessDefinitionWithSchema getProcessDefinitionWithSchema(String definitionKey);

    List<MiranumProcessDefinition> getServiceDefinitions();

    boolean allowedToStartDefinition(final String userId, final List<String> groupIds, final String resourceId);


}
