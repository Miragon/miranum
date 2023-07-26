package io.miranum.platform.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Application class for starting the micro-service.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class S3IntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(S3IntegrationApplication.class, args);
    }

}
