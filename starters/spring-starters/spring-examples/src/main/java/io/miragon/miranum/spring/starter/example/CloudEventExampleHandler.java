package io.miragon.miranum.spring.starter.example;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.miragon.miranum.connect.cloudevents.CloudEventHandler;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Component
public class CloudEventExampleHandler implements CloudEventHandler {

    @Override
    public String getType() {
        return "io.miragon.miranum.starter.web.common.CloudEventExampleRequest";
    }

    @Override
    public Optional<CloudEvent> handleCloudEvent(CloudEvent cloudEvent) {
        return Optional.of(CloudEventBuilder.from(cloudEvent)
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("https://spring.io/foos"))
                .withType("io.miragon.miranum.starter.web.common.CloudEventExampleResponse")
                .withData(cloudEvent.getData().toBytes())
                .build());

    }
}
