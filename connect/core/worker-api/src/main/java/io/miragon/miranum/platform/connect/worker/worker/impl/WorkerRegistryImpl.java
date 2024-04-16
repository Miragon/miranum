package io.miragon.miranum.platform.connect.worker.worker.impl;

import io.miragon.miranum.platform.connect.worker.worker.api.WorkerSubscription;
import io.miragon.miranum.platform.connect.worker.worker.api.WorkerRegistry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerRegistryImpl implements WorkerRegistry {

    private final WorkerSubscription workerSubscription;

    @Override
    public void register(final WorkerExecutor executor) {
        this.workerSubscription.subscribe(executor);
    }
}
