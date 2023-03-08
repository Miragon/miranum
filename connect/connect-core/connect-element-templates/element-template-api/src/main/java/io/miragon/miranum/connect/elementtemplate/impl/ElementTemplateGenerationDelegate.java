package io.miragon.miranum.connect.elementtemplate.impl;

import io.miragon.miranum.connect.elementtemplate.api.GenerateElementTemplate;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ElementTemplateGenerationDelegate implements ApplicationContextAware {

    private ApplicationContext ctx;
    private final ElementTemplatesGenerator elementTemplatesGenerator;
    private final ElementTemplateInfoMapper elementTemplateInfoMapper;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeWorkerAfterStartup() {
        final List<ElementTemplateInfo> elementTemplateInfos = this.getAllElementTemplateInfos();
        this.elementTemplatesGenerator.generate(new GenerateElementTemplatesCommand(elementTemplateInfos));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    private List<ElementTemplateInfo> getAllElementTemplateInfos() {
            final List<ElementTemplateInfo> elementTemplateInfos = new ArrayList<>();

        final String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        // Iterate over the list of spring beans and get all methods annotated with the specific annotation
        for (final String beanDefinitionName : beanDefinitionNames) {
            final Object bean = ctx.getBean(beanDefinitionName);
            final Class<?> beanClass = bean.getClass();

            ReflectionUtils.doWithMethods(beanClass, method -> {
                // Check if the method is annotated with the specific annotation
                if (method.isAnnotationPresent(GenerateElementTemplate.class)) {
                    final GenerateElementTemplate annotInstance = method.getAnnotation(GenerateElementTemplate.class);
                    elementTemplateInfos.add(this.elementTemplateInfoMapper.map(annotInstance, method));
                }
            });
        }
        return elementTemplateInfos;
    }
}