package io.miranum.platform.deploymentserver.application.ports.out;

import io.miranum.platform.deploymentserver.domain.Deployment;
import io.miranum.platform.deploymentserver.domain.DeploymentStatus;

public interface DeployFilePort {

    DeploymentStatus deploy(final Deployment deployment, final String target);

}
