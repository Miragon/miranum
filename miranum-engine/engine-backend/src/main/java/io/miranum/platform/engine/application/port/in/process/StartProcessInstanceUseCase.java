package io.miranum.platform.engine.application.port.in.process;

import java.util.List;
import java.util.Map;

public interface StartProcessInstanceUseCase {
    void startInstance(String definitionKey, Map<String, Object> variables, String userId, List<String> groups);
}
