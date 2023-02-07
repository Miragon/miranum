package io.miragon.miranum.integrations.example.ldap.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

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
