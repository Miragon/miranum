package io.miranum.platform.engine.processconfig.domain.service;

import io.miranum.platform.engine.application.port.out.ProcessConfigPort;
import io.miranum.platform.engine.domain.processconfig.ProcessConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service to interact with the {@link ProcessConfig}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessConfigService {

    private final ProcessConfigPort processConfigPort;

    /**
     * Get process config by key.
     *
     * @param ref key of the process config
     * @return process config
     */
    public ProcessConfig getProcessConfig(final String ref) {
        return this.processConfigPort.getByRef(ref);
    }


}
