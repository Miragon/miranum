package io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.configuration;

import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.adapter.out.MiranumDeploymentReceiverImpl;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.ports.in.DeployFile;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.usecase.DeployFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ComponentScan(basePackages = "io.miragon.miranum.platform.deploymentreceiver.adapter.in.rest")
public class ReceiverRestAutoconfiguration {

    @ConditionalOnMissingBean
    @Bean
    public MiranumDeploymentReceiver miranumDeployment() {
        return new MiranumDeploymentReceiverImpl();
    }

    @ConditionalOnMissingBean
    @Bean
    public DeployFile deployFile(final MiranumDeploymentReceiver miranumDeployment) {
        return new DeployFileUseCase(miranumDeployment);
    }

}
