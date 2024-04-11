package io.miranum.platform.engine;

import io.miranum.platform.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miranum.platform.engine.adapter.in.deployment.EngineDeploymentReceiver;
import io.miranum.platform.engine.adapter.out.schema.SchemaClient;
import io.miranum.platform.engine.application.port.in.deployment.ArtifactDeploymentUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"io.miranum.platform.engine"})
@EnableJpaRepositories(basePackages = {"io.miranum.platform.engine"})
@ComponentScan(basePackages = {"io.miranum.platform.engine"})
@EnableFeignClients(clients = {SchemaClient.class})
public class MiranumEngineConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MiranumDeploymentReceiver miranumDeploymentReceiver(final ArtifactDeploymentUseCase artifactDeploymentUseCase) {
        return new EngineDeploymentReceiver(artifactDeploymentUseCase);
    }

}
