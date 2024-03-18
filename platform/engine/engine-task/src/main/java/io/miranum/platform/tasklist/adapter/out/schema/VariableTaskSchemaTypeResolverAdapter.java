package io.miranum.platform.tasklist.adapter.out.schema;

import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaTypeResolverPort;
import io.miranum.platform.tasklist.domain.TaskSchemaType;
import org.springframework.stereotype.Component;

@Component
public class VariableTaskSchemaTypeResolverAdapter implements TaskSchemaTypeResolverPort {
  @Override
  public TaskSchemaType apply(Task task) {
    Object value = task.getPayload().getOrDefault(TaskVariables.TASK_SCHEMA_TYPE.getName(), null);
    if (value instanceof String) {
      return TaskSchemaType.valueOf((String) value);
    } else if (value instanceof TaskSchemaType){
      return (TaskSchemaType) value;
    } else {
      throw new IllegalStateException("Unknown value for task schema type " + value);
    }
    // FIXME camunda BPM Data
    //return reader(task.getPayload()).getOrDefault(TaskVariables.TASK_SCHEMA_TYPE, null);
  }
}
