package io.miragon.miranum.platform.deployment.application.ports.in.deployment;

import java.util.List;

public interface ArtifactDeploymentInPort {

    void deployBpmn(final String file, final String filename, final String namespace, final List<String> tags);

    void deployDmn(final String file, final String filename, final String namespace, final List<String> tags);
}
