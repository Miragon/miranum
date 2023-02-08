package io.miragon.miranum.connect.adapter.in.c7.process;

import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7ProcessAutoConfiguration {

    @Bean
    public Camunda7ProcessAdapter camunda7MessageAdapter(final ProcessDefinitionApi processDefinitionApi) {
        return new Camunda7ProcessAdapter(processDefinitionApi);
    }
}
