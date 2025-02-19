package io.miragon.miranum.inquiry.shared.process;

import dev.bpmcrafters.processengineapi.deploy.DeployBundleCommand;
import dev.bpmcrafters.processengineapi.deploy.DeploymentApi;
import dev.bpmcrafters.processengineapi.deploy.NamedResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class AutoDeployProcessesOnStartup implements ApplicationListener<ApplicationStartedEvent> {

    private final ResourcePatternResolver resourcePatternResolver;
    private final DeploymentApi deploymentApi;

    @Value("${io.miragon.miranum.inquiry.deployment.deployment-resource-pattern}")
    private String deploymentResourcePattern;

    @Override
    public void onApplicationEvent(final ApplicationStartedEvent event) {
        try {
            final Resource[] resources = resourcePatternResolver.getResources(deploymentResourcePattern);
            Arrays.stream(resources).forEach(resource -> {
                try {
                    deploymentApi.deploy(new DeployBundleCommand(List.of(new NamedResource(
                            resource.getFilename(), resource.getInputStream(), Map.of()
                    )), null));
                    log.info("Deployed resource '{}'", resource.getFilename());
                } catch (final IOException ex) {
                    log.warn("Failed to deploy resource '{}'", resource, ex);
                }
            });
        } catch (final Exception e) {
            log.warn("Failed to import resources from classpath with pattern '{}'", deploymentResourcePattern);
        }
    }

}
