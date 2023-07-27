package io.miranum.platform.engine.application.service;


import io.miranum.platform.engine.application.port.in.process.InitalizeStartContextUseCase;
import io.miranum.platform.engine.application.port.out.process.StartContextPort;
import io.miranum.platform.engine.domain.process.StartContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitalizeStartContextService implements InitalizeStartContextUseCase {

    private final StartContextPort startContextPort;

    @Override
    public void initalize(final String userId, final String processKey) {
        final StartContext startContext = new StartContext(userId, processKey);
        startContextPort.save(startContext);
    }
}
