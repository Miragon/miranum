package io.miranum.platform.deploymentreceiver.adapter.in.rest;

import io.miranum.platform.deploymentreceiver.application.dto.DeploymentDto;
import io.miranum.platform.deploymentreceiver.application.ports.in.DeployFile;
import io.miranum.platform.deploymentreceiver.domain.DeploymentStatus;
import io.miranum.platform.deploymentreceiver.adapter.in.rest.DeploymentReceiverController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

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
