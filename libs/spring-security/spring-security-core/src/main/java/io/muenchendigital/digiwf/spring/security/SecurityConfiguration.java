package io.muenchendigital.digiwf.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

import static io.muenchendigital.digiwf.spring.security.SecurityConfiguration.SECURITY;

/**
 * The central class for configuration of all security aspects.
 */
@Configuration
@Profile(SECURITY)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    /**
     * Activates security.
     */
    public static final String SECURITY = "!no-security";


    private final RestTemplateBuilder restTemplateBuilder;
    private final SpringSecurityProperties springSecurityProperties;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http, Converter<Jwt, AbstractAuthenticationToken> converter) throws Exception {
        // @formatter:off
    http
        .csrf()
        .ignoringAntMatchers(springSecurityProperties.getPermittedUrls())
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .antMatchers(springSecurityProperties.getPermittedUrls()).permitAll()
        .anyRequest().authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(converter)
        .and();
    return http.build();
    // @formatter:on
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }


}

