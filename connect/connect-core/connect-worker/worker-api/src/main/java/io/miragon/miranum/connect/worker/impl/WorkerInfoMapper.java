package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.shared.TooManyParametersException;
import io.miragon.miranum.connect.worker.api.Worker;

import java.lang.reflect.Method;

public class WorkerInfoMapper {

    public WorkerInfo map(final Worker worker, final Object bean, final Method method) {

        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new TooManyParametersException(worker);
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];
        return new WorkerInfo(worker.type(), worker.timeout(), bean, method, inputParameter, method.getReturnType());
    }

}