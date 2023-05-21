package io.miranum.platform.tasklist.domain;

import io.holunda.polyflow.view.Task;
import lombok.Data;

@Data
public class TaskWithSchema {
    private final Task task;
    private final JsonSchema schema;
}
