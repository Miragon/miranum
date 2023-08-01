package io.miranum.platform.tasklist.connector.listener;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.*;
import static io.miranum.platform.tasklist.connector.listener.TaskSchemaType.SCHEMA_BASED;

@Component
public class TaskSchemaTypeCreateTaskListener {

    public static final VariableFactory<TaskSchemaType> TASK_SCHEMA_TYPE = customVariable("app_task_schema_type", TaskSchemaType.class);

    @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
    @EventListener(condition = "#task.eventName.equals('create')")
    public void taskCreated(final DelegateTask task) {
        if (reader(task).getLocalOptional(TASK_SCHEMA_TYPE).isPresent()) {
            return;
        }
        val writer = writer(task);
        writer.setLocal(TASK_SCHEMA_TYPE, SCHEMA_BASED);
    }
}
