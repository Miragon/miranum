package io.miranum.platform.engine.application.service;


import io.miranum.platform.engine.application.port.in.process.InitializeStartContextUseCase;
import io.miranum.platform.engine.application.port.out.process.StartContextPort;
import io.miranum.platform.engine.domain.process.StartContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitalizeStartContextService implements InitializeStartContextUseCase {

    private final StartContextPort startContextPort;

    @Override
    public void initialize(final String userId, final String processKey) {
        final StartContext startContext = new StartContext(userId, processKey);
        startContextPort.save(startContext);
    }
}
