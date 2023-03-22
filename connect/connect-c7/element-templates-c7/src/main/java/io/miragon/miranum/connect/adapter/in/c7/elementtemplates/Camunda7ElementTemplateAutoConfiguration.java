package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import io.miragon.miranum.connect.elementtemplate.impl.GenerateElementTemplatePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Camunda7ElementTemplateAutoConfiguration {

    @Bean
    public GenerateElementTemplatePort camunda7ElementTemplateGenerator() {
        return new Camunda7ElementTemplateGenerator();
    }
}