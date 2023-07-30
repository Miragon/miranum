package io.miranum.platform.engine.application.port.in.process;

import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinitionWithSchema;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProcessDefinitionQuery {

    Page<MiranumProcessDefinition> getProcessDefinitions(String userId, List<String> groups, int page, int size, String query);

    MiranumProcessDefinitionWithSchema getProcessDefinitionWithSchema(String userId, List<String> groups, final String key);

}
