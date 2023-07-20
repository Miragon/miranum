package io.miranum.platform.tasklist.application.port.out.cancellation;

import io.holunda.polyflow.view.Task;

import java.util.function.Function;

/**
 * Resolves the cancellation information for the task.
 */
public interface CancellationFlagOutPort extends Function<Task, Boolean>  {
}
