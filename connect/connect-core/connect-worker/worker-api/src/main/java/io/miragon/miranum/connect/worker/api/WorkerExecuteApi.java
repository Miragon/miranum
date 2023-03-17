package io.miragon.miranum.connect.worker.api;

import io.miragon.miranum.connect.worker.impl.WorkerInfo;

/**
 * Interface to execute a worker.
 */
public interface WorkerExecuteApi {

    /**
     * Registers a worker.
     *
     * @param workerInfo worker to be registered
     */
    void register(final WorkerInfo workerInfo);

    /**
     * Executes a worker.
     *
     * @param data data to be processed
     * @param type type of the worker
     * @return result of the worker
     */
    Object execute(final String type, final Object data);

}
