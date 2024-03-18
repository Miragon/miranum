package io.miranum.platform.tasklist.adapter.out.schema;

import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaRefResolverPort;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;

/**
 * Resolves task schema reference based on a task variable.
 * Returns null if the variable is not found.
 */
@Component
public class VariableTaskSchemaResolverAdapter implements TaskSchemaRefResolverPort {
  @Override
  public String apply(Task task) {
    return reader(task.getPayload()).getOrDefault(TaskVariables.TASK_SCHEMA_KEY, null);
  }
}
