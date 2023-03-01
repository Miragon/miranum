package io.miragon.miraum.fitconnect.integration.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fitconnect")
@Getter
@Setter
public class FitConnectProperties {
    private String baseUrl;
}