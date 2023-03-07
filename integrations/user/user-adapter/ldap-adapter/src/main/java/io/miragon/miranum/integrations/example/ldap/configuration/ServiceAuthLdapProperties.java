package io.miragon.miranum.integrations.example.ldap.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "miranum.ldap")
public class ServiceAuthLdapProperties {

    @NotBlank
    private String contextSource;

    @NotBlank
    private String personSearchBase;

    @NotBlank
    private String personObjectClasses;

    @NotBlank
    private String ouSearchBase;

    @NotBlank
    private String ouObjectClasses;

}
