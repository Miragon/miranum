package io.miragon.miranum.platform.deployment.application;

import io.miragon.miranum.platform.deployment.application.ports.in.deployment.ArtifactDeploymentInPort;
import io.miragon.miranum.platform.deployment.application.ports.out.engine.ArtifactDeploymentOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeploymentUseCase implements ArtifactDeploymentInPort {

    private final ArtifactDeploymentOutPort artifactDeploymentOutPort;

    @Override
    public void deployBpmn(String file, String filename, String namespace, List<String> tags) {
        this.artifactDeploymentOutPort.deployBpmn(file, filename, namespace, tags);
    }

    @Override
    public void deployDmn(String file, String filename, String namespace, List<String> tags) {
        this.artifactDeploymentOutPort.deployDmn(file, filename, namespace, tags);
    }
}
