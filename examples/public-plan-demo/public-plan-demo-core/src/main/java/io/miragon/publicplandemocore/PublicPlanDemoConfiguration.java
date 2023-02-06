package io.miragon.publicplandemocore;

import io.miragon.publicplandemocore.application.port.in.GeneratePDFUseCase;
import io.miragon.publicplandemocore.application.port.in.StorePDFUseCase;
import io.miragon.publicplandemocore.application.port.out.GeneratePDFPort;
import io.miragon.publicplandemocore.application.port.out.StorePDFPort;
import io.miragon.publicplandemocore.application.service.GeneratePDFService;
import io.miragon.publicplandemocore.application.service.StorePDFService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class PublicPlanDemoConfiguration {

    private final StorePDFPort storePDFPort;
    private final GeneratePDFPort generatePDFPort;

    @Bean
    public GeneratePDFUseCase generatePDFUseCase(){
        return new GeneratePDFService(this.generatePDFPort);
    }

    @Bean
    public StorePDFUseCase storePDFUseCase() {
        return new StorePDFService(this.storePDFPort);
    }

    @Bean
    public void tenantInterceptor() {}

}
