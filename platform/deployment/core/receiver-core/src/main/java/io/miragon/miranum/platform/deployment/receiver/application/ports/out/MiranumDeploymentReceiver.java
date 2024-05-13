package io.miragon.miranum.platform.deployment.receiver.application.ports.out;

import io.miragon.miranum.platform.deployment.receiver.domain.Deployment;

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
