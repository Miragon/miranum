package io.miragon.miranum.connect.binder.application.service;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteUseCaseCommand;
import io.miragon.miranum.connect.binder.application.port.out.ExecuteUseCaseInterceptor;
import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.TechnicalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Log
@RequiredArgsConstructor
public class ExecuteMethodService implements ExecuteMethodUseCase {

    private final List<ExecuteUseCaseInterceptor> interceptors;


    @Override
    public Object execute(final ExecuteUseCaseCommand command) throws BusinessException, TechnicalException {
        try {
            //1. execute interceptors if present
            this.interceptors.forEach(obj -> obj.intercept(command.getData(), command.getUseCase()));
            //2. execute methods
            return command.getUseCase().getMethod().invoke(command.getUseCase().getInstance(), command.getData());
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
