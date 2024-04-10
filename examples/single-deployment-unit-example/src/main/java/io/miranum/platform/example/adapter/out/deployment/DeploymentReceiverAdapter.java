package io.miranum.platform.example.adapter.out.deployment;

import io.miranum.platform.deploymentreceiver.application.DeploymentFailedException;
import io.miranum.platform.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miranum.platform.deploymentreceiver.domain.Deployment;
import io.miranum.platform.example.adapter.out.deployment.handler.DeploymentHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeploymentReceiverAdapter implements MiranumDeploymentReceiver {

    private final List<DeploymentHandler> deploymentHandlers;

    @Override
    public void deploy(final Deployment deployment) {
        this.deploymentHandlers.stream()
                .filter(handler -> handler.isResponsibleFor(deployment.getType()))
                .findFirst()
                .ifPresentOrElse(handler -> handler.deployArtifact(deployment), () -> {
                    throw new DeploymentFailedException("No handler found for deployment type " + deployment.getType());
                });
    }

}
