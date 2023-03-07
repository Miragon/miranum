package io.miragon.miranum.connect;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.miragon.miranum.connect.cloudevents.CloudEventHandler;
import io.miragon.miranum.connect.cloudevents.types.PublishMessageResponseSchema;
import io.miragon.miranum.connect.json.api.JsonSchema;
import io.miragon.miranum.connect.json.impl.JsonSchemaFactory;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.UUID;

@Component
public class MessageCloudEventHandler implements CloudEventHandler {
    @Autowired
    private ResourceLoader resourceLoader;

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
        var validationErrors = jsonSchema.validate(cloudEvent.getData());

        if(!validationErrors.isEmpty()) {
            throw new Exception("Validation Error");
        }



        PublishMessageResponseSchema res = new PublishMessageResponseSchema();
        res.setKey(123D);



        // Todo parse CloudEvent to Java POJO


        return Optional.of(CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("https://spring.io/foos"))
                .withType("io.miranum.bpmn.command.v1.PublishMessageResponse")
                .withData("Hello World".getBytes())
                .build());

    }
}
