package io.miranum.platform.connect.json.registry;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "io.miranum.platform.connect.json.registry")
@ComponentScan(basePackages = "io.miranum.platform.connect.json.registry")
@EnableJpaRepositories(basePackages = "io.miranum.platform.connect.json.registry")
public class MiranumSchemaConfiguration {

}

