package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.worker.api.WorkerRegistry;

import java.util.ArrayList;
import java.util.List;

public class WorkerRegistryImpl implements WorkerRegistry {

    private final List<WorkerExecutor> workerExecutors = new ArrayList<>();


    /**
     * Registers a Worker.
     *
     * @param WorkerExecutor Worker to be registered
     */
    @Override
    public void register(final WorkerExecutor WorkerExecutor) {
        this.workerExecutors.add(WorkerExecutor);
    }


    @Override
    public List<WorkerExecutor> availableWorkerExecutors() {
        return this.workerExecutors;
    }
}
