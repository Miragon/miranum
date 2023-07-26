package io.miranum.platform.tasklist.domain;

import io.holunda.polyflow.view.Task;
import lombok.Data;

@Data
public class TaskWithSchemaRef {
  private final Task task;
  private final String schemaRef;
  private final boolean cancelable;
  private final TaskSchemaType schemaType;
}
