package io.miragon.miranum.platform.connect.camunda7.remote.worker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "miranum.camunda7.worker")
@Getter
@Setter
public class Camunda7WorkerProperties {

    private int defaultRetries = 3;
}
