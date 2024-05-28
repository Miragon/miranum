package io.miragon.miranum.platform.example.adapter.out.deployment;

import io.miragon.miranum.platform.deployment.receiver.application.ports.out.MiranumDeploymentReceiver;
import io.miragon.miranum.platform.deployment.receiver.domain.Deployment;
import io.miragon.miranum.platform.engine.application.port.in.deployment.ArtifactDeploymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeploymentReceiverAdapter implements MiranumDeploymentReceiver {

    private final ArtifactDeploymentUseCase artifactDeploymentUseCase;

    @Override
    public void deploy(final Deployment deployment) {
        if (deployment.getType().equalsIgnoreCase("bpmn")) {
            log.info("Deploying BPMN artifact: {}", deployment.getFilename());
            this.artifactDeploymentUseCase.deployBpmn(deployment.getFile(), deployment.getFilename(), deployment.getNamespace(), deployment.getTags());
        }
        if (deployment.getType().equalsIgnoreCase("dmn")) {
            log.info("Deploying DMN artifact: {}", deployment.getFilename());
            this.artifactDeploymentUseCase.deployDmn(deployment.getFile(), deployment.getFilename(), deployment.getNamespace(), deployment.getTags());
        }

        // TODO implement forms and config deployments
    }

}
