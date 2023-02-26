package io.miragon.miranum.gateway.c7;

import io.miragon.miranum.connect.cloudevents.CloudEventsAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

@SpringBootApplication
@Import(CloudEventsAutoConfiguration.class)
public class GatewayC7Application {

    public static void main(final String[] args) {
        SpringApplication.run(GatewayC7Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Registered Beans: ");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}
