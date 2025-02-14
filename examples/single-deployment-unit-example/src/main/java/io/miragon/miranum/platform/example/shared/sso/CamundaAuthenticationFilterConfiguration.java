

package io.miragon.miranum.platform.example.shared.sso;

import io.miragon.miranum.platform.example.shared.sso.rest.ServiceAccountAuthenticationProvider;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Camunda ENGINE Security configuration.
 * Adds the filter retrieving currently logged-in user and setting Camunda authorization to it for all REST requests.
 */
@Configuration
public class CamundaAuthenticationFilterConfiguration {

    @Bean
    public FilterRegistrationBean<?> statelessUserAuthenticationFilter(
            IdentityService identityService,
            ServiceAccountAuthenticationProvider userProvider

    ) {
        final FilterRegistrationBean<CamundaUserAuthenticationFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new CamundaUserAuthenticationFilter(
                identityService,
                userProvider
        ));
        filterRegistration.setOrder(102); // make sure the filter is registered after the Spring Security Filter Chain
        // install the filter on all protected URLs to propagate the identity from the token to Camunda and Identity Service.
        filterRegistration.addUrlPatterns(
                "/engine-rest/*" // camunda rest api should be protected
        );
        return filterRegistration;
    }
}
