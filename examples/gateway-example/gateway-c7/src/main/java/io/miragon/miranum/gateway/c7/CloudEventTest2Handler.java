package io.miragon.miranum.gateway.c7;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.miragon.miranum.connect.cloudevents.CloudEventHandler;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Component
public class CloudEventTest2Handler implements CloudEventHandler {

    @Override
    public String getType() {
        return "test2";
    }

    @Override
    public Optional<CloudEvent> handleCloudEvent(CloudEvent cloudEvent) {
        return Optional.of(CloudEventBuilder.from(cloudEvent)
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("https://spring.io/foos"))
                .withType("io.spring.event.Test2")
                .withData(cloudEvent.getData().toBytes())
                .build());

    }
}
