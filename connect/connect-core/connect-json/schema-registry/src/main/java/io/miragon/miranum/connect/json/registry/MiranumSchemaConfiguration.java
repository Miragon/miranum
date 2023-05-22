package io.miragon.miranum.connect.json.registry;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "io.miragon.miranum.connect.json.registry")
@ComponentScan(basePackages = "io.miragon.miranum.connect.json.registry")
@EnableJpaRepositories(basePackages = "io.miragon.miranum.connect.json.registry")
public class MiranumSchemaConfiguration {

}

