package io.miragon.miranum.platform.deployment.adapter.in.deployment;

import io.miragon.miranum.platform.deployment.application.ports.in.deployment.ArtifactDeploymentInPort;
import io.miragon.miranum.platform.deployment.receiver.application.ports.out.MiranumDeploymentReceiver;
import io.miragon.miranum.platform.deployment.receiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class DeploymentReceiver implements MiranumDeploymentReceiver {

    private final ArtifactDeploymentInPort artifactDeploymentInPort;

    @Override
    public void deploy(final Deployment deployment) {
        if (deployment.getType().equalsIgnoreCase("bpmn")) {
            log.debug("Deploying BPMN artifact: {}", deployment.getFilename());
            this.artifactDeploymentInPort.deployBpmn(deployment.getFile(), deployment.getFilename(), deployment.getNamespace(), deployment.getTags());
        }
        if (deployment.getType().equalsIgnoreCase("dmn")) {
            log.debug("Deploying DMN artifact: {}", deployment.getFilename());
            this.artifactDeploymentInPort.deployDmn(deployment.getFile(), deployment.getFilename(), deployment.getNamespace(), deployment.getTags());
        }
    }
}
