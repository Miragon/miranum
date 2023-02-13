package io.miragon.miranum.connect.worker.impl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerInitalizer {

    private final BindWorkerPort bindWorkerPort;

    public void initialize(final InitializeWorkerCommand initializeWorkerCommand) {
        initializeWorkerCommand.getWorkerList().forEach(this.bindWorkerPort::bind);
    }
}
