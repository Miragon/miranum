package io.miranum.platform.tasklist.configuration;

import io.miranum.platform.tasklist.adapter.out.schema.JsonSchemaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {JsonSchemaClient.class})
public class SchemaConfiguration {
}
