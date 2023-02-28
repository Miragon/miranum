package io.miragon.miraum.fitconnect.integration.configuration;

import io.miragon.miraum.fitconnect.integration.gen.ApiClient;
import io.miragon.miraum.fitconnect.integration.gen.api.EinreichungsbermittlungApi;
import io.miragon.miraum.fitconnect.integration.gen.api.EinreichungsempfangApi;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(FitConnectProperties.class)
@AllArgsConstructor
public class FitConnectAutoConfiguration {

    private final FitConnectProperties fitConnectProperties;

    @Bean
    public EinreichungsbermittlungApi einreichungsbermittlungApi(final ApiClient apiClient) {
        return new EinreichungsbermittlungApi(apiClient);
    }

    @Bean
    public EinreichungsempfangApi einreichungsempfangApi(final ApiClient apiClient) {
        return new EinreichungsempfangApi(apiClient);
    }

    @Bean
    public ApiClient fitConnectClient(final ClientRegistrationRepository clientRegistrationRepository,
                                      final OAuth2AuthorizedClientService authorizedClientService) {
        return new ApiClient(webClient(clientRegistrationRepository, authorizedClientService));
    }

    private WebClient webClient(
            final ClientRegistrationRepository clientRegistrationRepository,
            final OAuth2AuthorizedClientService authorizedClientService
    ) {
        final var oauth = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService
                )
        );

        oauth.setDefaultClientRegistrationId("fitconnect");
        return WebClient.builder()
                .baseUrl(fitConnectProperties.getBaseUrl())
                .apply(oauth.oauth2Configuration())
                .build();
    }
}