package io.miragon.miranum.platform.deployment.receiver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DeploymentStatus {
    private boolean success;
    private String message;
}
