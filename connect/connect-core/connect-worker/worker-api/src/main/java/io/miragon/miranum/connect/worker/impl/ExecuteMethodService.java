package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import io.miragon.miranum.connect.worker.api.WorkerInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Log
@RequiredArgsConstructor
public class ExecuteMethodService implements ExecuteMethodUseCase {

    private final List<WorkerInterceptor> interceptors;


    @Override
    public Object execute(final ExecuteMethodCommand command) throws BusinessException, TechnicalException {
        try {
            // 1. execute interceptors if present
            this.interceptors.forEach(obj -> obj.intercept(command.getData(), command.getWorker()));
            // 2. execute methods
            return command.getWorker().getMethod().invoke(command.getWorker().getInstance(), command.getData());
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            // Unwrap the exception wrapped by the InvocationTargetException
            throw (RuntimeException) e.getTargetException();
        }
    }
}
