package io.miranum.platform.tasklist.application.port.out.schema;

import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.domain.TaskSchemaType;

import java.util.function.Function;

/**
 * Resolves the schema type for task.
 */
public interface TaskSchemaTypeResolverPort extends Function<Task, TaskSchemaType> {

}
