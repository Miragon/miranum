package io.miranum.platform.deploymentserver.configuration;

import io.miranum.platform.deploymentserver.adapter.out.MiranumRestDeployment;
import io.miranum.platform.deploymentserver.application.ports.in.DeployArtifact;
import io.miranum.platform.deploymentserver.application.ports.out.DeployFilePort;
import io.miranum.platform.deploymentserver.application.usecase.DeployArtifactUseCase;
import io.miranum.platform.deploymentserver.properties.DeploymentServerRestProperties;
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
@ComponentScan(basePackages = {"io.miranum.platform.deploymentserver.adapter.out", "io.miranum.platform.deploymentserver.adapter.in.rest"})
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
