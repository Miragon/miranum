/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.s3.integration.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;


/**
 * This class provides the {@link ForwardedHeaderFilter} to handle
 * the headers of type "Forwarded" and "X-Forwarded-*".
 */
@Configuration
public class ForwardedHeaderConfiguration {

    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        final FilterRegistrationBean<ForwardedHeaderFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ForwardedHeaderFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

}
