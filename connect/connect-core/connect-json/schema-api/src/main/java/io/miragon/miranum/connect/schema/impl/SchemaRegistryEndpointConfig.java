package io.miragon.miranum.connect.schema.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class SchemaRegistryEndpointConfig {

    @Value("${miranum.schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean
    public WebClient schemaRegistryClient() {
        return WebClient.create(schemaRegistryUrl);
    }

}
