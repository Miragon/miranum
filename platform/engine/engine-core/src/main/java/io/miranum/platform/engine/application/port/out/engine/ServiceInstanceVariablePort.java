package io.miranum.platform.engine.application.port.out.engine;

import java.util.Map;

public interface ServiceInstanceVariablePort {

    Map<String, Object> getVariables(final String instanceId);
}
