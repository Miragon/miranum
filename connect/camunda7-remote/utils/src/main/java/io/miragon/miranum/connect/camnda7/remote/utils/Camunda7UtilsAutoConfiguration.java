package io.miragon.miranum.connect.camnda7.remote.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
