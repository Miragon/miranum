package io.miragon.miranum.connect.worker.api;


import io.miragon.miranum.connect.worker.impl.WorkerInfo;

/**
 * Interceptor that can be used to execute code before the actual use case is called.
 */
public interface WorkerInterceptor {

    void intercept(Object data, WorkerInfo useCaseInfo);

}
