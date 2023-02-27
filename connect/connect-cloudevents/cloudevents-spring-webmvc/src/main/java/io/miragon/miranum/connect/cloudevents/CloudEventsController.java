package io.miragon.miranum.connect.cloudevents;

import io.cloudevents.CloudEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("cloudevents")
public class CloudEventsController {
    @Autowired
    CloudEventHandler cloudEventHandler;

    @PostMapping(value = "/")
    public CloudEvent consumeCloudEvent(@RequestBody CloudEvent cloudEvent) throws IOException {
        if (!cloudEvent.getType().equals(cloudEventHandler.getType()))
            throw new IllegalStateException("Unsupported CloudEvent Type: " + cloudEvent.getType());

         return cloudEventHandler.handleCloudEvent(cloudEvent)
                 .orElse(null);
    }
}
