package io.miranum.platform.connect.adapter.in.c7.process;

import io.miranum.platform.connect.c7.utils.Camunda7RestValueMapper;
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
