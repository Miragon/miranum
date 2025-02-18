package io.miragon.miranum.platform.deployment;

import io.miragon.miranum.platform.deployment.adapter.in.deployment.DeploymentReceiver;
import io.miragon.miranum.platform.deployment.application.ports.in.deployment.ArtifactDeploymentInPort;
import io.miragon.miranum.platform.deployment.receiver.application.ports.out.MiranumDeploymentReceiver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.miragon.miranum.platform.deployment"})
public class MiranumDeploymentConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MiranumDeploymentReceiver miranumDeploymentReceiver(final ArtifactDeploymentInPort artifactDeploymentInPort) {
        return new DeploymentReceiver(artifactDeploymentInPort);
    }

}
