package io.miragon.miranum.connect.adapter.in.c7.process;

import io.miragon.miranum.connect.c7.utils.Camunda7RestValueMapper;
import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7ProcessAutoConfiguration {

    @Bean
    public Camunda7ProcessAdapter camunda7ProcessAdapter(final ProcessDefinitionApi processDefinitionApi, final Camunda7RestValueMapper camunda7BaseVariableValueMapper) {
        return new Camunda7ProcessAdapter(processDefinitionApi, camunda7BaseVariableValueMapper);
    }
}
