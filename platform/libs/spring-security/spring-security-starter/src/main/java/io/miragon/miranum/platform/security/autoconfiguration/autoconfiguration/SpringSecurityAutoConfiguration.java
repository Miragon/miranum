package io.miragon.miranum.platform.security.autoconfiguration.autoconfiguration;


import io.miragon.miranum.platform.security.security.SpringSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Auto-configuration used to configure Spring security.
 */
@EnableConfigurationProperties(SpringSecurityProperties.class)
@ComponentScan(basePackages = "io.miragon.miranum.platform.security")
@PropertySource(value = {"classpath:application-spring-security-starter.yaml"}, factory = YamlPropertySourceFactory.class)
public class SpringSecurityAutoConfiguration {
}
