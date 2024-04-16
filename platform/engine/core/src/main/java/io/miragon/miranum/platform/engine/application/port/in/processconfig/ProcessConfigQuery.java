package io.miragon.miranum.platform.engine.application.port.in.processconfig;

import io.miragon.miranum.platform.engine.domain.process.ProcessConfig;

public interface ProcessConfigQuery {

    ProcessConfig getByRef(final String ref);
}
