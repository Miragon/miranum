package io.miragon.miranum.platform.deployment.server.domain;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentStatus implements Serializable {
    private boolean success;
    private String message;
}
