package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.worker.api.Worker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

@Log
@RequiredArgsConstructor
public class WorkerAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final WorkerInfoMapper workerInfoMapper;
    private final WorkerInfoRegistry workerInfoRegistry;

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        ReflectionUtils.doWithMethods(bean.getClass(), method -> {
            if (method.isAnnotationPresent(Worker.class)) {
                final Worker annotInstance = method.getAnnotation(Worker.class);
                workerInfoRegistry.addWorkerInfo(this.workerInfoMapper.map(annotInstance, bean, method));
            }
        });
        return bean;
    }
}