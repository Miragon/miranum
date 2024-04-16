package io.miragon.miranum.platform.deploymentserver.deploymentserver.application.ports.in;

import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.dto.DeploymentDto;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.dto.DeploymentSuccessDto;

public interface DeployArtifact {

    DeploymentSuccessDto deploy(final DeploymentDto deploymentDto);

}
