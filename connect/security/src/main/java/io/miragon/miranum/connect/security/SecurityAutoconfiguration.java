package io.miragon.miranum.connect.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties({SecurityProperties.class})
@ComponentScan(basePackages = "io.miragon.miranum.connect.security")
@PropertySource(value = "classpath:miranum-security-application.yaml", factory = YamlPropertySourceFactory.class)
public class SecurityAutoconfiguration {
}
