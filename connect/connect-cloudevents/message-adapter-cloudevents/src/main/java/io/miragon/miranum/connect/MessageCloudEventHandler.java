package io.miragon.miranum.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.jackson.PojoCloudEventDataMapper;
import io.miragon.miranum.connect.cloudevents.CloudEventHandler;
import io.miragon.miranum.connect.cloudevents.types.PublishMessageRequestSchema;
import io.miragon.miranum.connect.cloudevents.types.PublishMessageResponseSchema;
import io.miragon.miranum.connect.json.api.JsonSchema;
import io.miragon.miranum.connect.json.impl.JsonSchemaFactory;
import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.api.MessageApi;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static io.cloudevents.core.CloudEventUtils.mapData;

@Component
public class MessageCloudEventHandler implements CloudEventHandler {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private MessageApi messageApi;
    @Autowired
    private ObjectMapper objectMapper;

    private JsonSchema jsonSchema;

    @PostConstruct
    void init() throws IOException {
        var resource = resourceLoader.getResource("classpath:schemas/PublishMessageRequest.schema.json");
        jsonSchema = JsonSchemaFactory.createJsonSchema(resource.getContentAsString(Charset.forName("utf-8")));
    }

    @Override
    public String getType() {
        return "io.miranum.bpmn.command.v1.PublishMessageRequest";
    }

    @SneakyThrows
    @Override
    public Optional<CloudEvent> handleCloudEvent(CloudEvent cloudEvent) {
        PojoCloudEventData<PublishMessageRequestSchema> cloudEventData = mapData(
                cloudEvent,
                PojoCloudEventDataMapper.from(objectMapper, PublishMessageRequestSchema.class)
        );
        var publishMessageRequest = cloudEventData.getValue();

        var validationErrors = jsonSchema.validate(publishMessageRequest);

        if (!validationErrors.isEmpty()) {
            throw new Exception("Validation Error");
        }

        messageApi.correlateMessage(new CorrelateMessageCommand(publishMessageRequest.getName(), publishMessageRequest.getCorrelationKey(), objectMapper.readValue(publishMessageRequest.getVariables(), Map.class)));

        PublishMessageResponseSchema res = new PublishMessageResponseSchema();

        return Optional.of(CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("https://spring.io/foos"))
                .withType("io.miranum.bpmn.command.v1.PublishMessageResponse")
                .withData(objectMapper.writeValueAsString(res).getBytes())
                .build());

    }
}
