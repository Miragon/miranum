package io.miragon.miranum.connect.cloudevents;

import io.cloudevents.CloudEvent;

import java.util.Optional;

public interface CloudEventHandler {
    String getType();
    Optional<CloudEvent> handleCloudEvent(CloudEvent cloudEvent);
}
