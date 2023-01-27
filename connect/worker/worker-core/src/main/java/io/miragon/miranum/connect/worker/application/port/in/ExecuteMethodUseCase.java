package io.miragon.miranum.connect.binder.worker.application.port.in;

import io.miragon.miranum.connect.binder.worker.domain.BusinessException;
import io.miragon.miranum.connect.binder.worker.domain.TechnicalException;

public interface ExecuteMethodUseCase {

    Object execute(ExecuteMethodCommand command) throws BusinessException, TechnicalException;

}
