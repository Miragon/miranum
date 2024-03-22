package io.miranum.platform.connect.c7.utils;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7UtilsAutoConfiguration {

    @Bean
    public Camunda7RestValueMapper camunda7RestValueMapper() {
        return new Camunda7RestValueMapper();
    }

    @Bean
    public Camunda7PojoMapper camunda7PojoMapper() {
        return new Camunda7PojoMapper();
    }
}
