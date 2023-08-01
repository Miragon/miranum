package io.miranum.platform.tasklist.connector.listener;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.polyflow.taskpool.collector.task.TaskEventCollectorService;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;


@Component
public class TaskDescriptionCreateTaskListener {

    public static final VariableFactory<String> TASK_DESCRIPTION = stringVariable("app_task_description");

    @Order(TaskEventCollectorService.ORDER - 1000) // be before polyflow
    @EventListener(condition = "#task.eventName.equals('create')")
    public void taskCreated(final DelegateTask task) {

        val executionReader = reader(task.getExecution());
        val taskReader = reader(task);

        val description = executionReader.getLocalOptional(TASK_DESCRIPTION)
                .orElseGet(() -> taskReader.getLocalOptional(TASK_DESCRIPTION).orElse(null));

        if (description != null) {
            task.setDescription(description);
        }
    }
}
