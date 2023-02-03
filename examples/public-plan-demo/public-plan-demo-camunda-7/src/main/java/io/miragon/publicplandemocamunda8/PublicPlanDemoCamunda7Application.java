package io.miragon.publicplandemocamunda8;

import io.miragon.publicplandemocore.PublicPlanDemoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(PublicPlanDemoConfiguration.class)
public class PublicPlanDemoCamunda7Application {

    public static void main(String[] args) {
        SpringApplication.run(PublicPlanDemoCamunda7Application.class, args);
    }
}
