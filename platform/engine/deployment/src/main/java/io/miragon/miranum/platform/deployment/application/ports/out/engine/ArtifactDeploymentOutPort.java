package io.miragon.miranum.platform.deployment.application.ports.out.engine;

import java.util.List;

public interface ArtifactDeploymentOutPort {

    void deployBpmn(final String file, final String filename, final String namespace, final List<String> tags);

    void deployDmn(final String file, final String filename, final String namespace, final List<String> tags);
}
