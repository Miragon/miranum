package io.miragon.miraum.fitconnect.integration.configuration;

import io.miragon.miraum.fitconnect.integration.gen.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FitConnectAutoConfiguration {

    @Bean
    public ApiClient fitConnectClient(final ClientRegistrationRepository clientRegistrationRepository,
                                      final OAuth2AuthorizedClientService authorizedClientService) {
        final ApiClient apiClient = new ApiClient(this.webClient(clientRegistrationRepository, authorizedClientService));
        apiClient.setBasePath("https://submission-api-testing.fit-connect.fitko.dev");
        return apiClient;
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
        oauth.setDefaultClientRegistrationId("fit-connect");
        return WebClient.builder()
                .baseUrl("https://submission-api-testing.fit-connect.fitko.dev")
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(32 * 1024 * 1024))
                        .build())
                .apply(oauth.oauth2Configuration())
                .build();
    }
}