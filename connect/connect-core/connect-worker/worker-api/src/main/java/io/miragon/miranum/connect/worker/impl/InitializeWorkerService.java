package io.miragon.miranum.connect.worker.impl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InitializeWorkerService implements InitializeWorkerUseCase {

    private final BindWorkerPort bindWorkerPort;

    @Override
    public void initialize(final InitializeWorkerCommand initializeWorkerCommand) {
        initializeWorkerCommand.getWorkerList().forEach(this.bindWorkerPort::bind);
    }
}
