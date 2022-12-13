package io.miragon.miranum.connect.binder.application.port.in;

import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.TechnicalException;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;

public interface ExecuteMethodUseCase {

    Object execute(Object data, UseCaseInfo useCaseInfo) throws BusinessException, TechnicalException;

}
