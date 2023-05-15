package io.miragon.miranum.connect.json.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FlywayProperties.class})
public class MiranumSchemaRegistryApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MiranumSchemaRegistryApplication.class, args);
    }

}

