package io.miragon.miranum.platform.deploymentserver.deploymentserver.configuration;

import io.miragon.miranum.platform.deploymentserver.deploymentserver.adapter.out.MiranumRestDeployment;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.ports.in.DeployArtifact;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.ports.out.DeployFilePort;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.usecase.DeployArtifactUseCase;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.properties.DeploymentServerRestProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@ComponentScan(basePackages = {"io.miragon.miranum.platform.deploymentserver.adapter.out", "io.miragon.miranum.platform.deploymentserver.adapter.in.rest"})
@EnableConfigurationProperties(DeploymentServerRestProperties.class)
public class DeploymentServerRestAutoconfiguration {

    private final DeploymentServerRestProperties deploymentServerRestProperties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "io.miranum.deploymentserver.rest.enabled", havingValue = "true")
    public DeployFilePort deployFilePort() {
        // create a new MiranumRestDeployment instance with the targets and no customRequestInterceptors
        return new MiranumRestDeployment(this.deploymentServerRestProperties.getTargets(), List.of());
    }

    @Bean
    @ConditionalOnMissingBean
    public DeployArtifact deployArtifact(final DeployFilePort deployFilePort) {
        return new DeployArtifactUseCase(deployFilePort);
    }

}
