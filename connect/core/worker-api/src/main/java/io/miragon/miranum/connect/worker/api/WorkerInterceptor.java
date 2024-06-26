package io.miragon.miranum.connect.worker.api;

import io.miragon.miranum.connect.worker.impl.WorkerExecutor;

/**
 * Interceptor that can be used to execute code before the actual use case is called.
 */
public interface WorkerInterceptor {

    /**
     * Intercepts the use case execution.
     *
     * @param data     data to be processed
     * @param executor use case info
     */
    void intercept(WorkerExecutor executor, Object data);
}
