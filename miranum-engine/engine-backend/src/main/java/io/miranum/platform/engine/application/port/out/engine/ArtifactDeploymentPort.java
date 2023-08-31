package io.miranum.platform.engine.application.port.out.engine;

import java.util.List;

public interface ArtifactDeploymentPort {

    void deployBpmn(final String file, final String filename, final String namespace, final List<String> tags);

    void deployDmn(final String file, final String filename, final String namespace, final List<String> tags);
}
