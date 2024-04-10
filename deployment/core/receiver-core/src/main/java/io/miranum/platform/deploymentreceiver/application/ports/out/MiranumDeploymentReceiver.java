package io.miranum.platform.deploymentreceiver.application.ports.out;

import io.miranum.platform.deploymentreceiver.domain.Deployment;

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
