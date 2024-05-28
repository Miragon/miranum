package io.miragon.miranum.platform.deployment.receiver.application.ports.in;

import io.miragon.miranum.platform.deployment.receiver.application.dto.DeploymentDto;
import io.miragon.miranum.platform.deployment.receiver.domain.DeploymentStatus;

public interface DeployFile {

    DeploymentStatus deploy(final DeploymentDto deployment);

}
