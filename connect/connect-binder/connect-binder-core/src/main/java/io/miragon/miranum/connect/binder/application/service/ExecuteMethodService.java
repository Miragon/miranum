package io.miragon.miranum.connect.binder.application.service;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.TechnicalException;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.lang.reflect.InvocationTargetException;

@Log
@RequiredArgsConstructor
public class ExecuteMethodService implements ExecuteMethodUseCase {

    @Override
    public Object execute(final Object data, final UseCaseInfo useCaseInfo) throws BusinessException, TechnicalException {
        try {
            return useCaseInfo.getMethod().invoke(useCaseInfo.getInstance(), data);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
