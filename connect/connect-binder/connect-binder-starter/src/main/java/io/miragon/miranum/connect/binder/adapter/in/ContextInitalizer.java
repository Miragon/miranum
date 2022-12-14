package io.miragon.miranum.connect.binder.adapter.in;

import io.miragon.miranum.connect.binder.application.port.in.InitalizeWorkerCommand;
import io.miragon.miranum.connect.binder.application.port.in.InitializeUseCase;
import io.miragon.miranum.connect.binder.domain.Worker;
import io.miragon.miranum.connect.binder.domain.WorkerInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ContextInitalizer implements ApplicationContextAware {
    private ApplicationContext ctx;
    private final InitializeUseCase initializeUseCase;
    private final WorkerInfoMapper workerInfoMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        final List<WorkerInfo> workerInfos = this.getAllWorkerInfos();
        this.initializeUseCase.initalize(new InitalizeWorkerCommand(workerInfos));
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    private List<WorkerInfo> getAllWorkerInfos() {
        final List<WorkerInfo> workerInfos = new ArrayList<>();

        final String[] beanDefinitionNames = this.ctx.getBeanDefinitionNames();
        // Iterate over the list of spring beans and get all methods annotated with the specific annotation
        for (final String beanDefinitionName : beanDefinitionNames) {
            final Object bean = this.ctx.getBean(beanDefinitionName);
            final Class<?> beanClass = bean.getClass();

            ReflectionUtils.doWithMethods(beanClass, method -> {
                // Check if the method is annotated with the specific annotation
                if (method.isAnnotationPresent(Worker.class)) {
                    final Worker annotInstance = method.getAnnotation(Worker.class);
                    workerInfos.add(this.workerInfoMapper.map(annotInstance, bean, method));
                }
            });
        }
        return workerInfos;
    }

}
