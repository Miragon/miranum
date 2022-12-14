package io.miragon.miranum.connect.binder.application.port.out;

import io.miragon.miranum.connect.binder.domain.UseCaseInfo;

/**
 * Interceptor that can be used to execute code before the actual use case is called.
 */
public interface ExecuteUseCaseInterceptor {

    void intercept(Object data, UseCaseInfo useCaseInfo);

}
