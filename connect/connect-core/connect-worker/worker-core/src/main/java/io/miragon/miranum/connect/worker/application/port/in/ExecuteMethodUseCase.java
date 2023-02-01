package io.miragon.miranum.connect.worker.application.port.in;


import io.miragon.miranum.connect.worker.domain.BusinessException;
import io.miragon.miranum.connect.worker.domain.TechnicalException;

public interface ExecuteMethodUseCase {

    Object execute(ExecuteMethodCommand command) throws BusinessException, TechnicalException;

}
