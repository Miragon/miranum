package io.miranum.platform.deploymentserver.adapter.out;

import io.miranum.platform.deploymentreceiver.application.ports.in.DeployFile;
import io.miranum.platform.deploymentserver.application.ports.out.DeployFilePort;
import io.miranum.platform.deploymentserver.domain.Deployment;
import io.miranum.platform.deploymentserver.domain.DeploymentStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MiranumEmbeddedDeployment implements DeployFilePort {

    private final DeployFile deployFile;

    @Override
    public DeploymentStatus deploy(final Deployment deployment, final String target) {
        // map server deployment to receiver deployment
        // and map receiver deployment status to server deployment status
        final io.miranum.platform.deploymentreceiver.application.dto.DeploymentDto deplDto =
            io.miranum.platform.deploymentreceiver.application.dto.DeploymentDto.builder()
                .file(deployment.getFile())
                .type(deployment.getType())
                .filename(deployment.getFilename())
                .namespace(deployment.getNamespace())
                .tags(deployment.getTags())
                .build();
        final io.miranum.platform.deploymentreceiver.domain.DeploymentStatus status = this.deployFile.deploy(deplDto);
        return DeploymentStatus.builder()
            .success(status.isSuccess())
            .message(status.getMessage())
            .build();
    }
}
