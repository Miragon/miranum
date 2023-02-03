package io.miragon.publicplandemocore;

import io.miragon.publicplandemocore.application.port.in.GeneratePDFUseCase;
import io.miragon.publicplandemocore.application.port.in.StorePDFUseCase;
import io.miragon.publicplandemocore.application.service.GeneratePDFService;
import io.miragon.publicplandemocore.application.service.StorePDFService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublicPlanDemoConfiguration {

    @Bean
    public GeneratePDFUseCase generatePDFUseCase(){
        return new GeneratePDFService();
    }

    @Bean
    public StorePDFUseCase storePDFUseCase(){
        return new StorePDFService();
    }

    @Bean
    public void tenantInterceptor(){}

}
