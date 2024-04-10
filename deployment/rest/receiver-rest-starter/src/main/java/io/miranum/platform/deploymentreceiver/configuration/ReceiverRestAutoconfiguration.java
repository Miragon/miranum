package io.miranum.platform.deploymentreceiver.configuration;

import io.miranum.platform.deploymentreceiver.adapter.out.MiranumDeploymentReceiverImpl;
import io.miranum.platform.deploymentreceiver.application.ports.in.DeployFile;
import io.miranum.platform.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miranum.platform.deploymentreceiver.application.usecase.DeployFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ComponentScan(basePackages = "io.miranum.platform.deploymentreceiver.adapter.in.rest")
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
