package io.miragon.miranum.platform.example.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Notification {

    private String eventName;
    private String taskName;

    public String getTitle() {
        return eventName.equalsIgnoreCase("create") || eventName.equalsIgnoreCase("assignment") ?
                String.format("Task %s was assigned to you", taskName) :
                String.format("Task %s was completed", taskName);
    }

    public String getMessage() {
        return eventName.equalsIgnoreCase("create") || eventName.equalsIgnoreCase("assignment") ?
                "You have a new Task":
                "Task was completed";
    }

}
