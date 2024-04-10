package io.miranum.platform.example.adapter.out.deployment.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.miranum.platform.deploymentreceiver.application.DeploymentFailedException;
import io.miranum.platform.deploymentreceiver.domain.Deployment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfigurationDeploymentHandler implements DeploymentHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isResponsibleFor(final String artifactType) {
        return artifactType.equalsIgnoreCase("config");
    }

    @Override
    public void deployArtifact(final Deployment artifact) throws DeploymentFailedException {
        // TODO implement me
        log.info("Deploy file {} of type {} to namespace {} with tags {}",
                artifact.getFilename(), artifact.getType(), artifact.getNamespace(), artifact.getTags());
    }

}
