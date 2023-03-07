package io.miragon.miranum.connect;

import io.cloudevents.CloudEvent;
import io.cloudevents.CloudEventData;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.data.PojoCloudEventData;
import io.miragon.miranum.connect.cloudevents.CloudEventHandler;
import io.miragon.miranum.connect.cloudevents.types.PublishMessageResponseSchema;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Component
public class CloudEventsMessageHandler implements CloudEventHandler {
    @Override
    public String getType() {
        return "io.miranum.bpmn.command.v1.PublishMessageRequest";
    }

    @Override
    public Optional<CloudEvent> handleCloudEvent(CloudEvent cloudEvent) {

        // Todo parse CloudEvent to Java POJO

        PublishMessageResponseSchema res = new PublishMessageResponseSchema();
        res.setKey(123D);

        return Optional.of(CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("https://spring.io/foos"))
                .withType("io.miranum.bpmn.command.v1.PublishMessageResponse")
                .withData("Hello World".getBytes())
                .build());

    }
}
