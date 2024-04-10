package io.miranum.platform.example.adapter.out.deployment.handler;

import io.miranum.platform.deploymentreceiver.application.DeploymentFailedException;
import io.miranum.platform.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BpmnDeploymentHandler implements DeploymentHandler {
    @Override
    public boolean isResponsibleFor(final String artifactType) {
        return artifactType.equalsIgnoreCase("bpmn");
    }

    @Override
    public void deployArtifact(final Deployment artifact) throws DeploymentFailedException {
        // TODO implement me
        log.info("Deploy file {} of type {} to namespace {} with tags {}",
                artifact.getFilename(), artifact.getType(), artifact.getNamespace(), artifact.getTags());
    }

}
