package io.miranum.platform.engine.application.port.in.processconfig;

import io.miranum.platform.engine.domain.processconfig.ProcessConfig;

public interface ProcessConfigQuery {

    ProcessConfig getByRef(final String ref);
}
