package io.miranum.platform.engine.application.port.in.deployment;

import java.util.List;

public interface ArtifactDeploymentUseCase {

    void deployBpmn(final String file, final String filename, final String namespace, final List<String> tags);

    void deployDmn(final String file, final String filename, final String namespace, final List<String> tags);
}
