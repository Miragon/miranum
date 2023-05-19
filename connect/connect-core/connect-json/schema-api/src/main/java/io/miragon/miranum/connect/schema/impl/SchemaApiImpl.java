package io.miragon.miranum.connect.schema.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.schema.api.SchemaApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClientRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SchemaApiImpl implements SchemaApi {

    @Value("${miranum.schema.registry.timeout}")
    private Integer loadSchemaTimeout;

    private final ObjectMapper mapper = new ObjectMapper();

    private final WebClient schemaRegistryClient;

    @Override
    public JsonNode loadLatestSchema(String bundle, String ref) {
        String jsonNodeRaw =  schemaRegistryClient.get()
                .uri(String.format("/%s/%s", bundle, ref))
                .httpRequest(httpRequest -> {
                    HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
                    reactorRequest.responseTimeout(Duration.ofSeconds(loadSchemaTimeout));
                })
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class)
                .block();

            try {
                return mapper.readTree(jsonNodeRaw);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public JsonNode loadVersionedSchema(String bundle, String ref, Integer version) {
        String jsonNodeRaw =  schemaRegistryClient.get()
                .uri(String.format("/%s/%s/%s", bundle, ref, version))
                .httpRequest(httpRequest -> {
                    HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
                    reactorRequest.responseTimeout(Duration.ofSeconds(loadSchemaTimeout));
                })
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            return mapper.readTree(jsonNodeRaw);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
