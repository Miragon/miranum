package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.worker.api.WorkerRegistry;
import io.miragon.miranum.connect.worker.api.WorkerSubscription;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WorkerRegistryImpl implements WorkerRegistry {

    private final List<WorkerExecutor> workerExecutors = new ArrayList<>();

    private final WorkerSubscription workerSubscription;


    /**
     * Registers a Worker.
     *
     * @param executor Worker to be registered
     */
    @Override
    public void register(final WorkerExecutor executor) {
        this.workerExecutors.add(executor);
        this.workerSubscription.subscribe(executor);
    }

    @Override
    public List<WorkerExecutor> availableWorkerExecutors() {
        return this.workerExecutors;
    }
}
