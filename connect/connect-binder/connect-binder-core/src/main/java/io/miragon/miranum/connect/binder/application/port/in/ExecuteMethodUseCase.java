package io.miragon.miranum.connect.binder.application.port.in;

import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.TechnicalException;

public interface ExecuteMethodUseCase {

    Object execute(ExecuteUseCaseCommand command) throws BusinessException, TechnicalException;

}
