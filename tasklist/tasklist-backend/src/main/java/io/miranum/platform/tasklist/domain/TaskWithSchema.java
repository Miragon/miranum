package io.miranum.platform.tasklist.domain;

import io.holunda.polyflow.view.Task;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class TaskWithSchema {
  @NonNull
  private final Task task;
  @NonNull
  private final boolean cancelable;

  private final TaskSchemaType taskSchemaType;

  private final JsonSchema schema;
}
