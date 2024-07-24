package io.miragon.miranum.platform.example.shared.configuration;

import io.miragon.miranum.platform.example.engine.sso.GrantedAuthoritiesExtractor;
import io.miragon.miranum.platform.example.engine.sso.TokenParsingOAuth2UserService;
import io.miragon.miranum.platform.example.engine.sso.TokenParsingOidcUserService;
import io.miragon.miranum.platform.example.engine.sso.rest.RestExceptionHandler;
import io.miragon.miranum.platform.example.engine.sso.webapp.OAuthContainerBasedAuthenticationProvider;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.util.EnumSet;
import java.util.Set;

import static java.util.Collections.singletonMap;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@Profile("!no-security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final TokenParsingOAuth2UserService oAuth2UserService;
    private final GrantedAuthoritiesExtractor grantedAuthoritiesExtractor;

    @Value("${camunda.sso.webapps-role}")
    private String webappsRole;

    @Value("${camunda.sso.worker-role}")
    private String workerRole;

    private final String registration = "keycloak";

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        antMatcher("/api/**"),
                        antMatcher("/assets/**"),
                        antMatcher("/lib/**"),
                        antMatcher("/actuator/**"),
                        antMatcher("/camunda/**"),
                        antMatcher("/engine-rest/**")
                ))
                // oAuth2 login via keycloak
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                antMatcher("/camunda/**"),
                                antMatcher("/api/**"),
                                antMatcher("/assets/**"),
                                antMatcher("/lib/**")
                        ).hasRole(webappsRole)
                ).oauth2Login(
                        oauth2Login -> oauth2Login
                                .authorizationEndpoint(endpoint ->
                                        endpoint.baseUri("/camunda" + OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI)
                                )
                                .userInfoEndpoint(userinfo -> userinfo
                                        .userService(oAuth2UserService)
                                        .oidcUserService(new TokenParsingOidcUserService(oAuth2UserService))
                                )
                                .loginProcessingUrl("/camunda" + OAuth2LoginAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI)
                                .loginPage("/camunda" + OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + registration)
                )
                // oAuth2 service accounts via keycloak
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                antMatcher(HttpMethod.OPTIONS),
                                antMatcher("/actuator/**"),
                                antMatcher("/error"),
                                antMatcher("/public")
                        ).permitAll()
                        .requestMatchers(
                                antMatcher("/engine-rest/**"),
                                antMatcher("/api/**")
                        ).hasRole(workerRole)
                ).oauth2ResourceServer(resource -> resource
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor))
                ).build();
    }


    // The ForwardedHeaderFilter is required to correctly assemble the redirect URL for OAUth2 login. Without the filter, Spring generates an http URL even though the OpenShift
    // route is accessed through https.
    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        FilterRegistrationBean<ForwardedHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ForwardedHeaderFilter());
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }

    // This filter is responsible for integrating the camunda webapps security with spring security. It is configured with the ContainerBasedAuthenticationProvider defined below.
    @Bean
    public FilterRegistrationBean<ContainerBasedAuthenticationFilter> containerBasedAuthenticationFilterRegistrationBean() {
        FilterRegistrationBean<ContainerBasedAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(new ContainerBasedAuthenticationFilter());
        registrationBean.setInitParameters(singletonMap(ProcessEngineAuthenticationFilter.AUTHENTICATION_PROVIDER_PARAM, OAuthContainerBasedAuthenticationProvider.class.getName()));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
        return registrationBean;
    }

    // Quite a dirty hack to replace camunda's RestExceptionHandler with our own that logs exceptions more selectively.
    // Workaround for https://app.camunda.com/jira/browse/CAM-10799.
    @PostConstruct
    public void replaceRestExceptionHandler() {
        Set<Class<?>> configurationClasses = CamundaRestResources.getConfigurationClasses();
        configurationClasses.remove(org.camunda.bpm.engine.rest.exception.RestExceptionHandler.class);
        configurationClasses.add(RestExceptionHandler.class);
    }
}
