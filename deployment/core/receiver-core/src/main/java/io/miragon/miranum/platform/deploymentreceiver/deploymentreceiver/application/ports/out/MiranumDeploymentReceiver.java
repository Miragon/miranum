package io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.ports.out;

import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.domain.Deployment;

/**
 * Interface for miranum deployment.
 */
public interface MiranumDeploymentReceiver {

    /**
     * Deploy a file.
     *
     * @param deployment the deployment
     */
    void deploy(final Deployment deployment);

}
