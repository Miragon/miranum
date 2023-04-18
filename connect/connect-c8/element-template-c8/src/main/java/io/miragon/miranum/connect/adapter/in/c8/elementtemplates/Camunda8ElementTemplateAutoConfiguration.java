package io.miragon.miranum.connect.adapter.in.c8.elementtemplates;

import io.miragon.miranum.connect.elementtemplate.impl.GenerateElementTemplatePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Camunda8ElementTemplateAutoConfiguration
{

    @Bean
    public GenerateElementTemplatePort camunda8ElementTemplateGenerator() {
        return new Camunda8ElementTemplateGenerator();
    }
}
