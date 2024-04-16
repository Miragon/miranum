package io.miragon.miranum.platform.deploymentserver.deploymentserver.configuration;

import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.adapter.out.MiranumDeploymentReceiverImpl;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.ports.in.DeployFile;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.usecase.DeployFileUseCase;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.adapter.out.MiranumEmbeddedDeployment;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.ports.in.DeployArtifact;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.ports.out.DeployFilePort;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.usecase.DeployArtifactUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.miragon.miranum.platform.deploymentserver.adapter.out", "io.miragon.miranum.platform.deploymentserver.adapter.in.rest"})
public class DeploymentServerEmbeddedAutoconfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DeployArtifact deployArtifact(final DeployFilePort deployFilePort) {
        return new DeployArtifactUseCase(deployFilePort);
    }

    @Bean
    @ConditionalOnMissingBean
    public DeployFilePort deployFilePort(final DeployFile deployFile) {
        return new MiranumEmbeddedDeployment(deployFile);
    }

    @ConditionalOnMissingBean
    @Bean
    public DeployFile deployFile(final MiranumDeploymentReceiver miranumDeployment) {
        return new DeployFileUseCase(miranumDeployment);
    }

    @ConditionalOnMissingBean
    @Bean
    public MiranumDeploymentReceiver miranumDeployment() {
        return new MiranumDeploymentReceiverImpl();
    }

}
