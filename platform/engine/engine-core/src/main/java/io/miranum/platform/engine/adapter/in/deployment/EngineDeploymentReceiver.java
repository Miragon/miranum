package io.miranum.platform.engine.adapter.in.deployment;

import io.miragon.miranum.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miragon.miranum.deploymentreceiver.domain.Deployment;
import io.miranum.platform.engine.application.port.in.deployment.ArtifactDeploymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EngineDeploymentReceiver implements MiranumDeploymentReceiver {

    private final ArtifactDeploymentUseCase artifactDeploymentUseCase;

    @Override
    public void deploy(final Deployment deployment, final List<String> tags) {
        if (deployment.getType().equalsIgnoreCase("bpmn")) {
            log.info("Deploying BPMN artifact: {}", deployment.getFilename());
            this.artifactDeploymentUseCase.deployBpmn(deployment.getFile(), deployment.getFilename(), deployment.getNamespace(), tags);
        }
        if (deployment.getType().equalsIgnoreCase("dmn")) {
            log.info("Deploying DMN artifact: {}", deployment.getFilename());
            this.artifactDeploymentUseCase.deployDmn(deployment.getFile(), deployment.getFilename(), deployment.getNamespace(), tags);
        }
    }
}
