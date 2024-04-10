package io.miranum.platform.deploymentreceiver.application.ports.in;

import io.miranum.platform.deploymentreceiver.application.dto.DeploymentDto;
import io.miranum.platform.deploymentreceiver.domain.DeploymentStatus;

public interface DeployFile {

    DeploymentStatus deploy(final DeploymentDto deployment);

}
