package io.miranum.platform.tasklist.connector.listener;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.telemetry.TelemetryRegistry;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.miranum.platform.tasklist.connector.listener.CancelableTaskStatusCreateTaskListener.TASK_CANCELABLE;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.mockito.Mockito.mock;

class CancelableTaskStatusCreateTaskListenerTest {

    @RegisterExtension
    public static ProcessEngineExtension extension = ProcessEngineExtension
            .builder()
            .useProcessEngine(
                    ProcessEngineConfiguration
                            .createStandaloneInMemProcessEngineConfiguration()
                            .setHistory("none")
                            .setJdbcUrl("jdbc:h2:mem:camunda;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;")
                            .setSkipHistoryOptimisticLockingExceptions(true)
                            .setTelemetryRegistry(mock(TelemetryRegistry.class))
                            .buildProcessEngine()
            )
            .ensureCleanAfterTest(true)
            .build();
    private final RuntimeService runtimeService = extension.getRuntimeService();
    private final TaskService taskService = extension.getTaskService();

    @BeforeEach
    void init_bridge() {
        Mocks.register("bridgeTestListener", new BridgeTestListener(new CancelableTaskStatusCreateTaskListener()));
    }

    @Test
    @Deployment(resources = "process_cancelable_user_task.bpmn")
    public void is_cancellable() {

        val instance = runtimeService.startProcessInstanceByKey("process_cancelable_user_task");
        assertThat(instance).isStarted();
        assertThat(instance).isWaitingAt("user_task");

        val cancelable = reader(taskService, task().getId()).getLocal(TASK_CANCELABLE);
        Assertions.assertThat(cancelable).isTrue();
    }

    @Test
    @Deployment(resources = "process_cancelable_user_task_default_error_code.bpmn")
    public void is_cancellable_default_error_code() {

        val instance = runtimeService.startProcessInstanceByKey("process_cancelable_user_task");
        assertThat(instance).isStarted();
        assertThat(instance).isWaitingAt("user_task");

        val cancelable = reader(taskService, task().getId()).getLocal(TASK_CANCELABLE);
        Assertions.assertThat(cancelable).isTrue();
    }

    @Test
    @Deployment(resources = "process_non_cancelable_user_task_other_error_code.bpmn")
    public void is_not_cancellable_other_error_code() {

        val instance = runtimeService.startProcessInstanceByKey("process_cancelable_user_task");
        assertThat(instance).isStarted();
        assertThat(instance).isWaitingAt("user_task");

        val cancelable = reader(taskService, task().getId()).getLocal(TASK_CANCELABLE);
        Assertions.assertThat(cancelable).isFalse();
    }

    @Test
    @Deployment(resources = "process_non_cancelable_user_task.bpmn")
    public void is_not_cancellable() {

        val instance = runtimeService.startProcessInstanceByKey("process_cancelable_user_task");
        assertThat(instance).isStarted();
        assertThat(instance).isWaitingAt("user_task");

        val cancelable = reader(taskService, task().getId()).getLocal(TASK_CANCELABLE);
        Assertions.assertThat(cancelable).isFalse();
    }

    @Test
    @Deployment(resources = "process_cancelable_user_task_no_errorcode.bpmn")
    public void is_cancellable_no_errorcode() {

        val instance = runtimeService.startProcessInstanceByKey("process_cancelable_user_task");
        assertThat(instance).isStarted();
        assertThat(instance).isWaitingAt("user_task");

        val cancelable = reader(taskService, task().getId()).getLocal(TASK_CANCELABLE);
        Assertions.assertThat(cancelable).isTrue();
    }

    /**
     * Listener bridging the absence of spring.
     */
    @RequiredArgsConstructor
    private static class BridgeTestListener implements TaskListener {
        private final CancelableTaskStatusCreateTaskListener cancelableTaskStatusCreateTaskListener;

        @Override
        public void notify(DelegateTask delegateTask) {
            cancelableTaskStatusCreateTaskListener.taskCreated(delegateTask);
        }
    }
}