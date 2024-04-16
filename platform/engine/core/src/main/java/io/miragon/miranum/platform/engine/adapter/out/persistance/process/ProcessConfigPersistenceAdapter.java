package io.miragon.miranum.platform.engine.adapter.out.persistance.process;

import io.miragon.miranum.platform.engine.application.port.out.process.ProcessConfigPort;
import io.miragon.miranum.platform.engine.domain.process.ProcessConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessConfigPersistenceAdapter implements ProcessConfigPort {
    @Override
    public ProcessConfig getByRef(String schemaId) {
        return null;
    }
}
