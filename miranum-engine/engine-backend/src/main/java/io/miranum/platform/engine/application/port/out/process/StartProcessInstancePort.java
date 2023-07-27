package io.miranum.platform.engine.application.port.out.process;

import io.miranum.platform.engine.domain.process.MiranumProcessInstance;

import java.util.Map;

public interface StartProcessInstancePort {
    MiranumProcessInstance startProcessInstance(String definitionName, String definitionKey, Map<String, Object> variables);
}
