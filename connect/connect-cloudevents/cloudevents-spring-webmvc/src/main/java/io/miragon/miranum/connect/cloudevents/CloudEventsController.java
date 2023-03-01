package io.miragon.miranum.connect.cloudevents;

import io.cloudevents.CloudEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("cloudevents")
public class CloudEventsController {
    Map<String, CloudEventHandler> handlerMap;

    public CloudEventsController(Map<String, CloudEventHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @PostMapping(value = "/")
    public CloudEvent consumeCloudEvent(@RequestBody CloudEvent cloudEvent) throws IOException {
        if (!handlerMap.containsKey(cloudEvent.getType()))
            throw new IllegalStateException("Unsupported CloudEvent Type: " + cloudEvent.getType());

         return handlerMap.get(cloudEvent.getType())
                 .handleCloudEvent(cloudEvent)
                 .orElse(null);
    }
}
