package io.miragon.miranum.connect.security.client;

import feign.RequestInterceptor;
import io.miragon.miranum.connect.security.configuration.OAuth2AccessTokenSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

/**
 * Configures the OAuth2 request interceptor for Feign clients.
 */
@RequiredArgsConstructor
public class MiranumFeignOAuthClientConfig {

    private final OAuth2AccessTokenSupplier oAuth2AccessTokenSupplier;

    @ConditionalOnProperty(value = "miranum.security.service-account", matchIfMissing = true)
    @Bean
    public RequestInterceptor oAuth2RequestInterceptor() {
        return (requestTemplate ->
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + oAuth2AccessTokenSupplier.get().getTokenValue()));
    }

}
