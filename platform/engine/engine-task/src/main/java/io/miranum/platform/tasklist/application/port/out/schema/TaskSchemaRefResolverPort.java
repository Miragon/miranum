package io.miranum.platform.tasklist.application.port.out.schema;

import io.holunda.polyflow.view.Task;

import java.util.function.Function;

/**
 * Resolves the schema reference for task.
 */
public interface TaskSchemaRefResolverPort extends Function<Task, String> {

}
