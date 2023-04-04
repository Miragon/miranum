package io.miragon.miranum.connect.worker.impl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerInitializer {

    private final BindWorkerPort bindWorkerPort;

    public void initialize(final InitializeWorkerCommand initializeWorkerCommand) {
        initializeWorkerCommand.workerList().forEach(this.bindWorkerPort::bind);
    }
}
