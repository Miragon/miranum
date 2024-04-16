package io.miragon.miranum.platform.deploymentserver.deploymentserver.deployment;

import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.ports.out.MiranumDeploymentReceiver;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.domain.Deployment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExampleDeploymentReceiver implements MiranumDeploymentReceiver {

    @Override
    public void deploy(final Deployment deployment) {
        log.info("Deploy file {} of type {} to namespace {} with tags {}",
            deployment.getFilename(), deployment.getType(), deployment.getNamespace(), deployment.getTags());
    }

}
