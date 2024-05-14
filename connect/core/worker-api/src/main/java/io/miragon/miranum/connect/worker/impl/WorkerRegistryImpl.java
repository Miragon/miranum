package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.worker.api.WorkerRegistry;
import io.miragon.miranum.connect.worker.api.WorkerSubscription;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerRegistryImpl implements WorkerRegistry {

    private final WorkerSubscription workerSubscription;

    @Override
    public void register(final WorkerExecutor executor) {
        this.workerSubscription.subscribe(executor);
    }
}
