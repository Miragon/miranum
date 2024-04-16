package io.miragon.miranum.platform.deploymentserver.deploymentserver.adapter.out;

import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.dto.DeploymentDto;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.ports.in.DeployFile;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.ports.out.DeployFilePort;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.domain.Deployment;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.domain.DeploymentStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MiranumEmbeddedDeployment implements DeployFilePort {

    private final DeployFile deployFile;

    @Override
    public DeploymentStatus deploy(final Deployment deployment, final String target) {
        // map server deployment to receiver deployment
        // and map receiver deployment status to server deployment status
        final DeploymentDto deplDto =
            DeploymentDto.builder()
                .file(deployment.getFile())
                .type(deployment.getType())
                .filename(deployment.getFilename())
                .namespace(deployment.getNamespace())
                .tags(deployment.getTags())
                .build();
        final io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.domain.DeploymentStatus status = this.deployFile.deploy(deplDto);
        return DeploymentStatus.builder()
            .success(status.isSuccess())
            .message(status.getMessage())
            .build();
    }
}
