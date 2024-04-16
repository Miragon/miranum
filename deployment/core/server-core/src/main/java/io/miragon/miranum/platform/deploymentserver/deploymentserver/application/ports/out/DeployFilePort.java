package io.miragon.miranum.platform.deploymentserver.deploymentserver.application.ports.out;

import io.miragon.miranum.platform.deploymentserver.deploymentserver.domain.Deployment;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.domain.DeploymentStatus;

public interface DeployFilePort {

    DeploymentStatus deploy(final Deployment deployment, final String target);

}
