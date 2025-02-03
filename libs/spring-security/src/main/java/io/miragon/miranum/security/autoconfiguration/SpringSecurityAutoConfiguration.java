package io.miragon.miranum.security.autoconfiguration;


import io.miragon.miranum.security.SpringSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Auto-configuration used to configure Spring security.
 */
@EnableConfigurationProperties(SpringSecurityProperties.class)
@ComponentScan(basePackages = "io.miragon.miranum.security")
@PropertySource(value = {"classpath:application-spring-security-starter.yaml"}, factory = io.miragon.miranum.security.autoconfiguration.YamlPropertySourceFactory.class)
public class SpringSecurityAutoConfiguration {
}
