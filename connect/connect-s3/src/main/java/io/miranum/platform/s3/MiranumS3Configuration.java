package io.miranum.platform.s3;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"io.miranum.platform.s3"})
@EnableJpaRepositories(basePackages = {"io.miranum.platform.s3"})
@ComponentScan(basePackages = {"io.miranum.platform.s3"})
public class MiranumS3Configuration {


}
