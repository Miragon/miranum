package io.miragon.miranum.platform.deploymentreceiver.adapter.in.rest.rest;

import io.miragon.miranum.platform.deployment.receiver.adapter.in.rest.DeploymentReceiverController;
import io.miragon.miranum.platform.deployment.receiver.application.dto.DeploymentDto;
import io.miragon.miranum.platform.deployment.receiver.application.ports.in.DeployFile;
import io.miragon.miranum.platform.deployment.receiver.domain.DeploymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DeploymentControllerTest {

    private DeploymentReceiverController deploymentController;
    private final DeployFile deployFile = Mockito.mock(DeployFile.class);

    @BeforeEach
    void setUp() {
        this.deploymentController = new DeploymentReceiverController(this.deployFile);
    }

    @Test
    void deployTest() {
        final DeploymentDto deploymentDto = DeploymentDto.builder()
            .file("foobar")
            .filename("foo.bar")
            .namespace("test")
            .type("bpmn")
            .tags(null)
            .build();

        Mockito.when(this.deployFile.deploy(deploymentDto)).thenReturn(DeploymentStatus.builder()
            .success(true)
            .message("test")
            .build());
        final DeploymentStatus result = this.deploymentController.deploy(deploymentDto);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessage()).isEqualTo("test");
    }
}
