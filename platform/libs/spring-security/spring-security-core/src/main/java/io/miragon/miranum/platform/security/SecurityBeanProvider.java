package io.miragon.miranum.platform.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!no-security")
public class SecurityBeanProvider {

    @Bean
    @ConditionalOnMissingBean
    public SecurityConfiguration miranumSecurityConfiguration(SpringSecurityProperties springSecurityProperties) {
        return new SecurityConfiguration(springSecurityProperties);
    }

}
