package io.miragon.miranum.connect.cloudevents;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("cloudevents")
public class CloudEventsController {
    @PostMapping(value = "/")
    public CloudEvent consumeCloudEvent(@RequestBody CloudEvent cloudEvent) throws IOException {
        return CloudEventBuilder.from(cloudEvent)
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("https://spring.io/foos"))
                .withType("io.spring.event.Foo")
                .withData(cloudEvent.getData().toBytes())
                .build();


        /*
        if (!cloudEvent.getType().equals("app-b.MyCloudEvent")) {
            throw new IllegalStateException("Wrong Cloud Event Type, expected: 'app-b.MyCloudEvent' and got: " + cloudEvent.getType());
        }

     */
    }
}
