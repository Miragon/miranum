package io.muenchendigital.digiwf.spring.security.autoconfiguration;


import io.muenchendigital.digiwf.spring.security.SpringSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Auto-configuration used to configure Spring security.
 */
@EnableConfigurationProperties(SpringSecurityProperties.class)
@ComponentScan(basePackages = "io.muenchendigital.digiwf.spring.security")
public class SpringSecurityAutoConfiguration {
}
