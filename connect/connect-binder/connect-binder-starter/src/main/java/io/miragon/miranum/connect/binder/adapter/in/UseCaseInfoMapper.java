package io.miragon.miranum.connect.binder.adapter.in;

import io.miragon.miranum.connect.binder.domain.UseCase;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;

import java.lang.reflect.Method;

public class UseCaseInfoMapper {

    public UseCaseInfo map(final UseCase useCase, final Object bean, final Method method) {
        return new UseCaseInfo(useCase.type(), bean, method, method.getParameterTypes(), method.getReturnType());
    }

}
