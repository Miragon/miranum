package io.miragon.miranum.connect.worker.application.service;

import io.miragon.miranum.connect.worker.application.port.in.InitializeWorkerCommand;
import io.miragon.miranum.connect.worker.application.port.in.InitializeWorkerUseCase;
import io.miragon.miranum.connect.worker.application.port.out.BindWorkerPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InitializeWorkerService implements InitializeWorkerUseCase {

    private final BindWorkerPort bindWorkerPort;

    @Override
    public void initialize(final InitializeWorkerCommand initializeWorkerCommand) {
        initializeWorkerCommand.getWorkerList().forEach(this.bindWorkerPort::bind);
    }
}
