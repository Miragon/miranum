package io.miragon.miranum.connect.binder.application.service;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteUseCaseCommand;
import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.TechnicalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.lang.reflect.InvocationTargetException;

@Log
@RequiredArgsConstructor
public class ExecuteMethodService implements ExecuteMethodUseCase {

    @Override
    public Object execute(final ExecuteUseCaseCommand command) throws BusinessException, TechnicalException {
        try {
            return command.getUseCase().getMethod().invoke(command.getUseCase().getInstance(), command.getData());
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
