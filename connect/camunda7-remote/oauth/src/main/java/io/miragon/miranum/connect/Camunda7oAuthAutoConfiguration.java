package io.miragon.miranum.connect;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;
import org.camunda.community.rest.client.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
@Profile("!no-security")
public class Camunda7oAuthAutoConfiguration {

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String authServerUrl;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    private String cachedToken;
    private Instant tokenExpiry;
    private final ReentrantLock lock = new ReentrantLock();

    @Bean
    public ClientRequestInterceptor interceptor() {
        return context -> context.addHeader("Authorization", this.getAccessToken());
    }

    @Autowired
    public void addOAuthInterceptor(final ApiClient apiClient) {
        apiClient.setHttpClient(apiClient.getHttpClient().newBuilder().addInterceptor(this::intercept).build());
    }

    public Response intercept(final Interceptor.Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request requestWithToken = originalRequest.newBuilder()
                .header("Authorization", this.getAccessToken())
                .build();
        return chain.proceed(requestWithToken);
    }

    public String getAccessToken() {
        lock.lock();
        try {
            if (cachedToken == null || Instant.now().isAfter(tokenExpiry)) {
                fetchAndCacheToken();
            }
            return "Bearer " + cachedToken;
        } finally {
            lock.unlock();
        }
    }

    private void fetchAndCacheToken() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(this.clientId, this.clientSecret);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        final ResponseEntity<JsonNode> response = new RestTemplate().postForEntity(this.authServerUrl, request, JsonNode.class);

        JsonNode body = response.getBody();
        this.cachedToken = body.get("access_token").asText();
        int expiresIn = body.get("expires_in").asInt();
        this.tokenExpiry = Instant.now().plusSeconds(expiresIn - 10); // refresh token 10 sec before it actually expires
    }
}