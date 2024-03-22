package io.miranum.platform.connect.adapter.in.c7.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;
import org.camunda.community.rest.client.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class Camunda7oAuthAutoConfiguration {

    @Value("${miranum.c7.auth.server.url}")
    private String authServerUrl;

    @Value("${miranum.c7.client.id}")
    private String clientId;

    @Value("${miranum.c7.client.secret}")
    private String clientSecret;

    @Bean
    public ClientRequestInterceptor interceptor() {
        return context -> {
            context.addHeader("Authorization", this.getAccessToken());
        };
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
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", this.clientId);
        map.add("client_secret", this.clientSecret);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        final ResponseEntity<JsonNode> response = new RestTemplate().postForEntity(this.authServerUrl, request, JsonNode.class);
        return "Bearer " + response.getBody().get("access_token").asText();
    }

}
