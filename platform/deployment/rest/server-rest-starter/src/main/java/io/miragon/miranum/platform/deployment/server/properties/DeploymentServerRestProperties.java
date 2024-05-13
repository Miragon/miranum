package io.miragon.miranum.platform.deployment.server.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Setter
@Getter
@ConfigurationProperties(prefix = "io.miranum.platform.deployment.server.rest")
public class DeploymentServerRestProperties {
    private boolean enabled = true;
    private Map<String, Map<String, String>> targets;
}
