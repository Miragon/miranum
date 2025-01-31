package io.miragon.miranum.inquiry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.bpmcrafters.processengineapi", "org.camunda.community"})
public class InquiryIntegrationService {

    public static void main(final String[] args) {
        SpringApplication.run(InquiryIntegrationService.class, args);
    }

}
