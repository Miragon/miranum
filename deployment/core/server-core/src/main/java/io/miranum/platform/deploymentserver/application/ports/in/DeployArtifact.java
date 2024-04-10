package io.miranum.platform.deploymentserver.application.ports.in;

import io.miranum.platform.deploymentserver.application.dto.DeploymentDto;
import io.miranum.platform.deploymentserver.application.dto.DeploymentSuccessDto;

public interface DeployArtifact {

    DeploymentSuccessDto deploy(final DeploymentDto deploymentDto);

}
