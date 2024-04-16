package io.miragon.miranum.platform.deploymentserver.deploymentserver.adapter.in.rest;

import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.dto.ArtifactDto;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.dto.DeploymentDto;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.dto.DeploymentSuccessDto;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.dto.FileDto;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.application.ports.in.DeployArtifact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class DeploymentControllerTest {

    private DeploymentController deploymentController;
    private final DeployArtifact deployArtifact = Mockito.mock(DeployArtifact.class);

    @BeforeEach
    void setUp() {
        this.deploymentController = new DeploymentController(this.deployArtifact);
    }

    @Test
    void deployArtifactTest() {
        final FileDto fileDto = FileDto.builder()
            .name("test.bpmn")
            .content("foobar")
            .build();
        final ArtifactDto artifactDto = ArtifactDto.builder()
            .type("test")
            .project("test")
            .file(fileDto)
            .build();
        final DeploymentDto deploymentDto = DeploymentDto.builder()
            .target("test")
            .artifact(artifactDto)
            .build();

        Mockito.when(this.deployArtifact.deploy(deploymentDto)).thenReturn(DeploymentSuccessDto.builder()
            .success(true)
            .message("test")
            .build());

        final DeploymentSuccessDto status = this.deploymentController.deployArtifact(deploymentDto);

        assertThat(status.isSuccess()).isTrue();
        assertThat(status.getMessage()).isEqualTo("test");
    }

}
