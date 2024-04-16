package io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.ports.in;

import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.dto.DeploymentDto;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.domain.DeploymentStatus;

public interface DeployFile {

    DeploymentStatus deploy(final DeploymentDto deployment);

}
