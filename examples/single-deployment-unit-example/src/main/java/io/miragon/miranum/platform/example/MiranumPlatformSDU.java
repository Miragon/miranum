package io.miragon.miranum.platform.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * Single Deployment Unit
 */
@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class MiranumPlatformSDU {

    public static void main(final String[] args) {
        SpringApplication.run(MiranumPlatformSDU.class, args);
    }

}
