package io.miragon.miranum.connect.binder.adapter.in;

import io.miragon.miranum.connect.binder.domain.UseCase;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;

import java.lang.reflect.Method;

public class UseCaseInfoMapper {

    public UseCaseInfo map(final UseCase useCase, final Object bean, final Method method) {

        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new ToManyParametersExecption(useCase);
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];
        return new UseCaseInfo(useCase.type(), useCase.timeout(), bean, method, inputParameter, method.getReturnType());
    }

}
