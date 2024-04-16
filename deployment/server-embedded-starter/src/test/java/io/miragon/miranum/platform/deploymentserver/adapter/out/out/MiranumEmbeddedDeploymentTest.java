package io.miragon.miranum.platform.deploymentserver.adapter.out.out;

import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.dto.DeploymentDto;
import io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.application.ports.in.DeployFile;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.adapter.out.MiranumEmbeddedDeployment;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.ports.out.DeployFilePort;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.domain.Deployment;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.domain.DeploymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class MiranumEmbeddedDeploymentTest {

    private DeployFilePort miranumEmbeddedDeployment;
    private final DeployFile deployFile = Mockito.mock(DeployFile.class);

    @BeforeEach
    void setup() {
        miranumEmbeddedDeployment = new MiranumEmbeddedDeployment(this.deployFile);
    }

    @Test
    void deployTest() {
        final Deployment deployment = Deployment.builder()
            .file("foobar")
            .type("bpmn")
            .namespace("sampleNamespace")
            .tags(null)
            .build();
        final DeploymentStatus deploymentStatus = DeploymentStatus.builder()
            .success(true)
            .message("Deployment was successfully")
            .build();

        final DeploymentDto mappedDeployment = DeploymentDto.builder()
            .file(deployment.getFile())
            .type(deployment.getType())
            .namespace(deployment.getNamespace())
            .tags(deployment.getTags())
            .build();
        final io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.domain.DeploymentStatus mappedDeploymentStatus = io.miragon.miranum.platform.deploymentreceiver.deploymentreceiver.domain.DeploymentStatus.builder()
            .success(deploymentStatus.isSuccess())
            .message(deploymentStatus.getMessage())
            .build();

        Mockito.when(deployFile.deploy(mappedDeployment)).thenReturn(mappedDeploymentStatus);


        final DeploymentStatus result = this.miranumEmbeddedDeployment.deploy(deployment, "test");

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Deployment was successfully");
    }

}
