package io.miranum.platform.engine.application.port.out.process;

import io.miranum.platform.engine.domain.process.StartContext;

import java.util.Optional;

public interface StartContextPort {
    Optional<StartContext> searchStartContext(String userId, String definitionKey);

    void save(StartContext startContext);

    void delete(String id);
}
