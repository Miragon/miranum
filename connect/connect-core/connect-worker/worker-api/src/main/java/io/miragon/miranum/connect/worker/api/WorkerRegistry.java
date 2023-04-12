package io.miragon.miranum.connect.worker.api;

import io.miragon.miranum.connect.worker.impl.WorkerExecutor;

import java.util.List;

/**
 * Interface to execute a worker.
 */
public interface WorkerRegistry {

    /**
     * Registers a worker.
     *
     * @param workerExecutor worker to be registered
     */
    void register(final WorkerExecutor workerExecutor);


    /**
     * API to get all available workers.
     *
     * @return list of available workers
     */
    List<WorkerExecutor> availableWorkerExecutors();
}
