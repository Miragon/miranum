package io.miranum.platform.connect.adapter.in.c7.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.miranum.platform.connect.c7.utils.Camunda7PojoMapper;
import io.miranum.platform.connect.worker.api.WorkerExecuteApi;
import io.miranum.platform.connect.worker.impl.WorkerExecutor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.client.topic.TopicSubscription;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class Camunda7AdapterTest {

    private final ExternalTaskClient externalTaskClient =
            Mockito.mock(ExternalTaskClient.class);
    private final WorkerExecuteApi workerExecuteApi =
            Mockito.mock(WorkerExecuteApi.class);

    private final Camunda7PojoMapper mapper =
            Mockito.mock(Camunda7PojoMapper.class);

    private final Camunda7WorkerProperties properties =
            new Camunda7WorkerProperties();

    private final Camunda7WorkerAdapter adapter =
            new Camunda7WorkerAdapter(this.externalTaskClient, this.workerExecuteApi, mapper, properties);

    @Test
    void givenOneUseCase_thenExternalTaskClientSubscribesOnce() {
        final WorkerExecutor defaultWorker = this.givenDefaultExecutor("defaultWorker", 100L);
        final TopicSubscriptionBuilder builder = this.givenTopicSubscriptionBuilder();

        given(this.externalTaskClient.subscribe("defaultWorker")).willReturn(builder);

        this.adapter.subscribe(defaultWorker);

        then(this.externalTaskClient).should().subscribe("defaultWorker");
        then(this.externalTaskClient).shouldHaveNoMoreInteractions();

        then(builder).should().open();
        then(builder).should().handler(any());
        then(builder).should().lockDuration(100L);
        then(builder).shouldHaveNoMoreInteractions();
    }

    @Test
    void givenDefaultUseCaseAndSuccessfulTask_thenEverythingGetsExecuted() throws JsonProcessingException {
        final WorkerExecutor defaultWorker = this.givenDefaultExecutor("defaultWorker", 100L);
        final ExternalTask externalTask = this.givenDefaultTask();
        final ExternalTaskService service = this.givenExternalTaskService();
        final Map<String, Object> result = Map.of("value", "test");

        given(this.workerExecuteApi.execute(any(), any())).willReturn(result);
        given(mapper.mapFromEngineData(any())).willReturn(result);
        given(mapper.mapToEngineData(any())).willReturn(Variables.fromMap(result));

        this.adapter.execute(defaultWorker, externalTask, service);

        then(service).should().complete(externalTask, null, result);
    }

    @Test
    void givenDefaultUseCaseAndRetriesProvided_thenInvokeGetRemainingRetries() throws JsonProcessingException {
        final WorkerExecutor defaultWorker = givenDefaultExecutor("defaultWorker", 100L);
        final ExternalTask externalTask = givenDefaultTask();
        final ExternalTaskService service = givenExternalTaskService();
        final Map<String, Object> data = Map.of("retries", 3);
        properties.setDefaultRetries(5);

        given(externalTask.getRetries()).willReturn(null);
        given(mapper.mapFromEngineData(any())).willReturn(data);
        given(workerExecuteApi.execute(any(), any())).willThrow(new RuntimeException("test"));

        adapter.execute(defaultWorker, externalTask, service);

        var retriesCaptor = ArgumentCaptor.forClass(Integer.class);
        then(service).should().handleFailure((ExternalTask) any(), any(), any(), retriesCaptor.capture(), anyLong());
        assertEquals(3 - 1, retriesCaptor.getValue());
    }

    @Test
    void givenDefaultUseCaseAndRetriesSetInBpmn_thenUseBpmnRetries() {
        final WorkerExecutor defaultWorker = givenDefaultExecutor("defaultWorker", 100L);
        final ExternalTask externalTask = givenDefaultTask();
        final ExternalTaskService service = givenExternalTaskService();
        final int bpmnRetries = 5;
        given(externalTask.getRetries()).willReturn(bpmnRetries);
        given(workerExecuteApi.execute(any(), any())).willThrow(new RuntimeException("test"));

        adapter.execute(defaultWorker, externalTask, service);

        then(service).should().handleFailure((ExternalTask) any(), any(), any(), eq(bpmnRetries - 1), anyLong());
    }

    @Test
    void givenDefaultUseCaseAndRetriesSetInWorkerInput_thenUseWorkerRetries() {
        final WorkerExecutor defaultWorker = givenDefaultExecutor("defaultWorker", 100L);
        final ExternalTask externalTask = givenDefaultTask();
        final ExternalTaskService service = givenExternalTaskService();
        final int workerRetries = 4;
        final Map<String, Object> data = Map.of("retries", workerRetries);
        given(mapper.mapFromEngineData(any())).willReturn(data);
        given(externalTask.getRetries()).willReturn(null);
        given(workerExecuteApi.execute(any(), any())).willThrow(new RuntimeException("test"));

        adapter.execute(defaultWorker, externalTask, service);

        then(service).should().handleFailure((ExternalTask) any(), any(), any(), eq(workerRetries - 1), anyLong());
    }

    @Test
    void givenDefaultUseCaseAndNoRetriesProvided_thenUseDefaultRetries() {
        final WorkerExecutor defaultWorker = givenDefaultExecutor("defaultWorker", 100L);
        final ExternalTask externalTask = givenDefaultTask();
        final ExternalTaskService service = givenExternalTaskService();
        final int defaultRetries = properties.getDefaultRetries();
        given(externalTask.getRetries()).willReturn(null);
        given(workerExecuteApi.execute(any(), any())).willThrow(new RuntimeException("test"));

        adapter.execute(defaultWorker, externalTask, service);

        then(service).should().handleFailure((ExternalTask) any(), any(), any(), eq(defaultRetries - 1), anyLong());
    }

    @Test
    void givenDefaultUseCaseAndZeroRetriesProvided_thenUseZeroRetries() throws JsonProcessingException {
        final WorkerExecutor defaultWorker = givenDefaultExecutor("defaultWorker", 100L);
        final ExternalTask externalTask = givenDefaultTask();
        final ExternalTaskService service = givenExternalTaskService();
        final int workerRetries = 0;
        final Map<String, Object> data = Map.of("retries", workerRetries);
        given(mapper.mapFromEngineData(any())).willReturn(data);
        given(workerExecuteApi.execute(any(), any())).willThrow(new RuntimeException("test"));

        adapter.execute(defaultWorker, externalTask, service);

        then(service).should().handleFailure((ExternalTask) any(), any(), any(), eq(workerRetries), anyLong());
    }

    private WorkerExecutor givenDefaultExecutor(final String type, final Long lockDuration) {
        final WorkerExecutor workerExecutor = Mockito.mock(WorkerExecutor.class);
        given(workerExecutor.getType()).willReturn(type);
        given(workerExecutor.getTimeout()).willReturn(lockDuration);
        return workerExecutor;
    }

    private ExternalTask givenDefaultTask() {
        return Mockito.mock(ExternalTask.class);
    }

    private ExternalTaskService givenExternalTaskService() {
        return Mockito.mock(ExternalTaskService.class);
    }

    private TopicSubscriptionBuilder givenTopicSubscriptionBuilder() {
        final TopicSubscriptionBuilder builder = Mockito.mock(TopicSubscriptionBuilder.class);
        given(builder.handler(any())).willReturn(builder);
        given(builder.lockDuration(anyLong())).willReturn(builder);
        given(builder.open()).willReturn(Mockito.mock(TopicSubscription.class));
        return builder;
    }
}
