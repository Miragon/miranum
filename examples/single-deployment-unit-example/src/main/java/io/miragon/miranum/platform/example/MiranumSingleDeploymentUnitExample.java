package io.miragon.miranum.platform.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class MiranumSingleDeploymentUnitExample {

    public static void main(final String[] args) {
        SpringApplication.run(MiranumSingleDeploymentUnitExample.class, args);
    }

}
