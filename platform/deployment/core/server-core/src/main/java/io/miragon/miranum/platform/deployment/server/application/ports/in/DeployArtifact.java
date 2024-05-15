package io.miragon.miranum.platform.deployment.server.application.ports.in;

import io.miragon.miranum.platform.deployment.server.application.dto.DeploymentDto;
import io.miragon.miranum.platform.deployment.server.application.dto.DeploymentSuccessDto;

public interface DeployArtifact {

    DeploymentSuccessDto deploy(final DeploymentDto deploymentDto);

}
