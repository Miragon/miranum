package io.miranum.integration.s3.configuration;

import io.miranum.integration.s3.properties.S3IntegrationClientProperties;
import io.miranum.integration.s3.client.service.ApiClientFactory;
import io.miranum.integration.s3.gen.ApiClient;
import io.miranum.integration.s3.gen.api.FileApiApi;
import io.miranum.integration.s3.gen.api.FolderApiApi;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@ComponentScan(
        basePackages = {
                "io.miranum.integration.s3.gen",
                "io.miranum.integration.s3.client"
        },
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                /**
                                 * Exclude to avoid multiple instantiation of multiple beans with same name.
                                 * This class is instantiated in {@link S3IntegrationClientAutoConfiguration}
                                 * to give the bean another name.
                                 */
                                ApiClient.class,
                                FileApiApi.class,
                                FolderApiApi.class
                        }
                )
        })
@RequiredArgsConstructor
@EnableConfigurationProperties(S3IntegrationClientProperties.class)
public class S3IntegrationClientAutoConfiguration {

    public final S3IntegrationClientProperties s3IntegrationClientProperties;

    @Bean
    @ConditionalOnProperty(prefix = "io.miranum.integration.s3.client", name = "securityEnabled", havingValue = "true")
    public ApiClientFactory securedApiClientFactory(final ClientRegistrationRepository clientRegistrationRepository,
                                                    final OAuth2AuthorizedClientService authorizedClientService) {
        return new ApiClientFactory(
                this.s3IntegrationClientProperties.getDocumentStorageUrl(),
                this.webClient(clientRegistrationRepository, authorizedClientService)
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "io.miranum.integration.s3.client", name = "securityEnabled", havingValue = "false", matchIfMissing = true)
    public ApiClientFactory apiClientFactory() {
        return new ApiClientFactory(
                this.s3IntegrationClientProperties.getDocumentStorageUrl(),
                WebClient.builder().build()
        );
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
        oauth.setDefaultClientRegistrationId("s3");
        return WebClient.builder()
                .apply(oauth.oauth2Configuration())
                .build();
    }

}
