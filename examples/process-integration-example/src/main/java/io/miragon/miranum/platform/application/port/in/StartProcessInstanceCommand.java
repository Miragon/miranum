package io.miragon.miranum.platform.application.port.in;

import java.util.List;
import java.util.Map;


public record StartProcessInstanceCommand(
        String definitionKey,
        String correlationKey,
        Map<String, Object> variables,
        String userId,
        List<String> userGroups) {
}
