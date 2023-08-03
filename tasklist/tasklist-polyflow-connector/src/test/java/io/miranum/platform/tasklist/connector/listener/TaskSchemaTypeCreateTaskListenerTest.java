package io.miranum.platform.tasklist.connector.listener;

import lombok.val;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.community.mockito.delegate.DelegateTaskFake;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.miranum.platform.tasklist.connector.listener.TaskSchemaTypeCreateTaskListener.TASK_SCHEMA_TYPE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

class TaskSchemaTypeCreateTaskListenerTest {

    private final TaskSchemaTypeCreateTaskListener listener = new TaskSchemaTypeCreateTaskListener();
    private final ProcessEngineServices processEngineServices = mock(ProcessEngineServices.class);


    @Test
    public void detect_schema_based_form() {
        val task = new DelegateTaskFake(UUID.randomUUID().toString())
                .withProcessEngineServices(processEngineServices)
                .withProcessDefinitionId(UUID.randomUUID().toString())
                .withTaskDefinitionKey("task-key");

        listener.taskCreated(task);
        assertThat(task.getVariablesLocal()).containsEntry(TASK_SCHEMA_TYPE.getName(), TaskSchemaType.SCHEMA_BASED);
    }

}