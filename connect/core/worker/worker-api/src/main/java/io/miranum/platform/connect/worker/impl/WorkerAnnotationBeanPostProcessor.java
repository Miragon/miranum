package io.miranum.platform.connect.worker.impl;

import io.miranum.platform.connect.worker.api.Worker;
import io.miranum.platform.connect.worker.api.WorkerRegistry;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

@Log
@RequiredArgsConstructor
public class WorkerAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final WorkerRegistry workerRegistry;

    @Override
    public Object postProcessBeforeInitialization(final Object bean, @NonNull final String beanName) throws BeansException {
        ReflectionUtils.doWithMethods(bean.getClass(), method -> {
            if (method.isAnnotationPresent(Worker.class)) {
                final Worker annotInstance = method.getAnnotation(Worker.class);
                this.workerRegistry.register(this.buildWorker(annotInstance, bean, method));
            }
        });
        return bean;
    }

    private WorkerExecutor buildWorker(final Worker worker, final Object bean, final Method method) {
        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new IllegalArgumentException("Too many parameters");
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];
        return new WorkerExecutor(worker.type(), worker.timeout(), bean, method, inputParameter, method.getReturnType());
    }
}