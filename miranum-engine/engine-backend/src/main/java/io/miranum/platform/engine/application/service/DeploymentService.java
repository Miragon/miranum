package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.application.port.in.deployment.ArtifactDeploymentUseCase;
import io.miranum.platform.engine.application.port.out.engine.ArtifactDeploymentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeploymentService implements ArtifactDeploymentUseCase {
    private final ArtifactDeploymentPort artifactDeploymentPort;

    @Override
    public void deployBpmn(String file, String filename, String namespace, List<String> tags) {
        this.artifactDeploymentPort.deployBpmn(file, filename, namespace, tags);
    }

    @Override
    public void deployDmn(String file, String filename, String namespace, List<String> tags) {
        this.artifactDeploymentPort.deployDmn(file, filename, namespace, tags);
    }
}
