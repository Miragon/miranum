package io.miragon.miranum.platform.deployment.server.adapter.out;

import io.miragon.miranum.platform.deployment.receiver.application.dto.DeploymentDto;
import io.miragon.miranum.platform.deployment.receiver.application.ports.in.DeployFile;
import io.miragon.miranum.platform.deployment.server.application.ports.out.DeployFilePort;
import io.miragon.miranum.platform.deployment.server.domain.Deployment;
import io.miragon.miranum.platform.deployment.server.domain.DeploymentStatus;
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
        final io.miragon.miranum.platform.deployment.receiver.domain.DeploymentStatus status = this.deployFile.deploy(deplDto);
        return DeploymentStatus.builder()
            .success(status.isSuccess())
            .message(status.getMessage())
            .build();
    }
}
