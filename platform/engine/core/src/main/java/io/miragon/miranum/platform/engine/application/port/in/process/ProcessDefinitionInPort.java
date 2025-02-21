package io.miragon.miranum.platform.engine.application.port.in.process;

import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;

import java.util.List;

public interface ProcessDefinitionInPort {

    List<MiranumProcessDefinition> getProcessDefinitions(String userId, List<String> groups, String query);


}
