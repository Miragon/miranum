package io.miragon.miranum.connect.worker.adapter;


import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.connect.worker.domain.WorkerInfo;

import java.lang.reflect.Method;

public class WorkerInfoMapper {

    public WorkerInfo map(final Worker worker, final Object bean, final Method method) {

        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new ToManyParametersExecption(worker);
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];
        return new WorkerInfo(worker.type(), worker.timeout(), bean, method, inputParameter, method.getReturnType());
    }

}
