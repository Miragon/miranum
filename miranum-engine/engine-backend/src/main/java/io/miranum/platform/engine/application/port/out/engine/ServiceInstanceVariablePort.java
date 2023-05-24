package io.miranum.platform.engine.application.port.out;

import java.util.Map;

public interface ServiceInstanceVariablePort {

    Map<String, Object> getVariables(final String instanceId);
}
