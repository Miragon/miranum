package io.miragon.miranum.examples.waiter.adapter.in.miranum;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiranumFitConnectAdapterAutoConfiguration {

    @Bean
    public MiranumFitConnectAdapter miranumFitConnectAdapter() {
        return new MiranumFitConnectAdapter();
    }
}