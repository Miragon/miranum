package io.miragon.miranum.connect.binder.adapter.in;

import io.miragon.miranum.connect.binder.application.port.in.InitalizeUseCasesCommand;
import io.miragon.miranum.connect.binder.application.port.in.InitializeUseCase;
import io.miragon.miranum.connect.binder.domain.UseCase;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
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
    private final InitializeUseCase initalizeUseCasesService;
    private final UseCaseInfoMapper useCaseInfoMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        final List<UseCaseInfo> useCases = this.getAllUseCaseInfos();
        this.initalizeUseCasesService.initalize(new InitalizeUseCasesCommand(useCases));
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    private List<UseCaseInfo> getAllUseCaseInfos() {
        final List<UseCaseInfo> useCaseInfos = new ArrayList<>();

        final String[] beanDefinitionNames = this.ctx.getBeanDefinitionNames();
        // Iterate over the list of spring beans and get all methods annotated with the specific annotation
        for (final String beanDefinitionName : beanDefinitionNames) {
            final Object bean = this.ctx.getBean(beanDefinitionName);
            final Class<?> beanClass = bean.getClass();

            ReflectionUtils.doWithMethods(beanClass, method -> {
                // Check if the method is annotated with the specific annotation
                if (method.isAnnotationPresent(UseCase.class)) {
                    final UseCase annotInstance = method.getAnnotation(UseCase.class);
                    useCaseInfos.add(this.useCaseInfoMapper.map(annotInstance, bean, method));
                }
            });
        }
        return useCaseInfos;
    }

}
