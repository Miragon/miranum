package io.miragon.miranum.platform.application.port.out;

import java.util.Map;

public interface StartProcessInstanceOutPort {
    void startProcessInstance(String definitionKey, Map<String, Object> variables);
}
