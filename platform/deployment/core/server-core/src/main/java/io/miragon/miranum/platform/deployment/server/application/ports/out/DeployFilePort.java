package io.miragon.miranum.platform.deployment.server.application.ports.out;

import io.miragon.miranum.platform.deployment.server.domain.Deployment;
import io.miragon.miranum.platform.deployment.server.domain.DeploymentStatus;

public interface DeployFilePort {

    DeploymentStatus deploy(final Deployment deployment, final String target);

}
