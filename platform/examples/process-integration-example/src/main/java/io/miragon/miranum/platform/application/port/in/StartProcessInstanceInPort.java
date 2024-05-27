package io.miragon.miranum.platform.application.port.in;

import java.util.List;
import java.util.Map;

public interface StartProcessInstanceInPort {
    void startInstance(String definitionKey, Map<String, Object> variables, String userId, List<String> groups);
}
