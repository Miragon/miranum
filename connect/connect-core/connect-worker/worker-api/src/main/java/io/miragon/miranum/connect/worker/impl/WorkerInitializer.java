package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Initializes all workers after the application has started using the spring application ready event.
 */
@Component
@RequiredArgsConstructor
public class WorkerInitializer implements ApplicationContextAware {
    private ApplicationContext ctx;
    private final WorkerExecuteApi workerExecuteApi;

    /**
     * Initializes all workers after the application has started using the spring application ready event.
     * All beans with the {@link WorkerExecutor} annotation are registered as Workers.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initializeWorkersAfterStartup() {
        final List<WorkerExecutor> workerExecutors = this.getWorkers();
        workerExecutors.forEach(this.workerExecuteApi::register);
    }

    /**
     * Sets the application context.
     *
     * @param applicationContext application context
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    /**
     * Helper method to get all Workers (using the {@link WorkerExecutor} annotation) from the spring context.
     *
     * @return list of workers
     */
    private List<WorkerExecutor> getWorkers() {
        final List<WorkerExecutor> workerExecutors = new ArrayList<>();
        final String[] beanDefinitionNames = this.ctx.getBeanDefinitionNames();
        // Iterate over the list of spring beans and get all methods annotated with the specific annotation
        for (final String beanDefinitionName : beanDefinitionNames) {
            final Object bean = this.ctx.getBean(beanDefinitionName);
            final Class<?> beanClass = bean.getClass();

            ReflectionUtils.doWithMethods(beanClass, method -> {
                // Check if the method is annotated with the specific annotation
                if (method.isAnnotationPresent(Worker.class)) {
                    final Worker annotInstance = method.getAnnotation(Worker.class);
                    workerExecutors.add(this.buildWorker(annotInstance, bean, method));
                }
            });
        }
        return workerExecutors;
    }

    /**
     * Helper method to build a worker.
     *
     * @param worker    worker annotation
     * @param bean      bean
     * @param method    method
     * @return Worker
     */
    private WorkerExecutor buildWorker(final Worker worker, final Object bean, final Method method) {
        final Class<?>[] inputParameterTypes = method.getParameterTypes();

        if (inputParameterTypes.length > 1) {
            throw new IllegalArgumentException("Too many parameters");
        }

        final Class<?> inputParameter = inputParameterTypes.length == 0 ? null : inputParameterTypes[0];
        return new WorkerExecutor(worker.type(), worker.timeout(), bean, method, inputParameter, method.getReturnType());
    }

}
