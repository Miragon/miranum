package io.miranum.platform.tasklist.application.port.out.file;

import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.domain.TaskFileConfig;

import java.util.function.Function;

/**
 * Resolves the files variables for task.
 */
public interface TaskFileConfigResolverPort extends Function<Task, TaskFileConfig> {
}
