package io.miranum.platform.deploymentserver.configuration;

import io.miranum.platform.deploymentreceiver.adapter.out.MiranumDeploymentReceiverImpl;
import io.miranum.platform.deploymentreceiver.application.ports.in.DeployFile;
import io.miranum.platform.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miranum.platform.deploymentreceiver.application.usecase.DeployFileUseCase;
import io.miranum.platform.deploymentserver.adapter.out.MiranumEmbeddedDeployment;
import io.miranum.platform.deploymentserver.application.ports.in.DeployArtifact;
import io.miranum.platform.deploymentserver.application.ports.out.DeployFilePort;
import io.miranum.platform.deploymentserver.application.usecase.DeployArtifactUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.miranum.platform.deploymentserver.adapter.out", "io.miranum.platform.deploymentserver.adapter.in.rest"})
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
