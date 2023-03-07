package io.miragon.miranum.connect.elementtemplate;

import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateGenerator;
import io.miragon.miranum.connect.elementtemplate.impl.ElementTemplateInfoMapper;
import io.miragon.miranum.connect.elementtemplate.impl.GenerateElementTemplatePort;
import org.springframework.context.annotation.Bean;

public class ElementTemplateAutoConfiguration {

    @Bean
    public ElementTemplateInfoMapper elementTemplateInfoMapper() {
        return new ElementTemplateInfoMapper();
    }

    @Bean
    public ElementTemplateGenerator elementTemplateGenerator(final GenerateElementTemplatePort generateElementTemplatePort) {
        return new ElementTemplateGenerator(generateElementTemplatePort);
    }
}