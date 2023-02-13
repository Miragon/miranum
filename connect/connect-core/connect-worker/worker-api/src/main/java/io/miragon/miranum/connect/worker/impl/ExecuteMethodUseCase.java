package io.miragon.miranum.connect.worker.impl;


import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;

public interface ExecuteMethodUseCase {

    Object execute(ExecuteMethodCommand command) throws BusinessException, TechnicalException;

}
