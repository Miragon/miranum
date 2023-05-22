package io.miranum.platform.engine;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"io.miranum.platform.engine"})
@EnableJpaRepositories(basePackages = {"io.miranum.platform.engine"})
@ComponentScan(basePackages = {"io.miranum.platform.engine"})
public class MiranumEngineConfiguration {


}
