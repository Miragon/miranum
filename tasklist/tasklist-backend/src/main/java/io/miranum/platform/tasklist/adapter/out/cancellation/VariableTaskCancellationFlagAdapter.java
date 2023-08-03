package io.miranum.platform.tasklist.adapter.out.cancellation;

import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.application.port.out.cancellation.CancellationFlagOutPort;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.miranum.platform.tasklist.adapter.out.schema.TaskVariables.TASK_CANCELABLE;

/**
 * Checks if the task is cancellable based on task variable.
 */
@Component
public class VariableTaskCancellationFlagAdapter implements CancellationFlagOutPort {

  @Override
  public Boolean apply(Task task) {
    return reader(task.getPayload()).getOrDefault(TASK_CANCELABLE, false);
  }
}
