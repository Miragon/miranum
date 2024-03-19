package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.processconfig.ProcessConfigQuery;
import io.miranum.platform.engine.application.port.out.process.ProcessConfigPort;
import io.miranum.platform.engine.domain.process.ProcessConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service to interact with the {@link ProcessConfig}
 */
@Slf4j
@Service
@RequiredArgsConstructor
class ProcessConfigQueryService implements ProcessConfigQuery {

    private final ProcessConfigPort processConfigPort;

    /**
     * Get process config by key.
     *
     * @param ref key of the process config
     * @return process config
     */
    public ProcessConfig getByRef(final String ref) {
        return this.processConfigPort.getByRef(ref);
    }


}
