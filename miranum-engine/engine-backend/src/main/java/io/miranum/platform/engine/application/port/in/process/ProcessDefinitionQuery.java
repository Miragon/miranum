package io.miranum.platform.engine.application.port.in.process;

import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinitionWithSchema;

import java.util.List;

public interface ProcessDefinitionQuery {


    List<MiranumProcessDefinition> getProcessDefinitions(String userId, List<String> groups);


    MiranumProcessDefinitionWithSchema getProcessDefinitionWithSchema(String userId, List<String> groups, final String key);

}
