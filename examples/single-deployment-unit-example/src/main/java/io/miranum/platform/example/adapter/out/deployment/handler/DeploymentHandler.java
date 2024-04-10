package io.miranum.platform.example.adapter.out.deployment.handler;

import io.miranum.platform.deploymentreceiver.application.DeploymentFailedException;
import io.miranum.platform.deploymentreceiver.domain.Deployment;

/**
 * Handle deployment for specific types
 */
public interface DeploymentHandler {

    boolean isResponsibleFor(String artifactType);

    void deployArtifact(final Deployment artifact) throws DeploymentFailedException;


}
