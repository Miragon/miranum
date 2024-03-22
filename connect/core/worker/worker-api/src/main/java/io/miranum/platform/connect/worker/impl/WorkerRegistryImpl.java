package io.miranum.platform.connect.worker.impl;

import io.miranum.platform.connect.worker.api.WorkerRegistry;
import io.miranum.platform.connect.worker.api.WorkerSubscription;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerRegistryImpl implements WorkerRegistry {

    private final WorkerSubscription workerSubscription;

    @Override
    public void register(final WorkerExecutor executor) {
        this.workerSubscription.subscribe(executor);
    }
}