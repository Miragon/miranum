package io.miragon.miranum.connect.adapter.in.c7.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.miragon.miranum.connect.c7.utils.Camunda7PojoMapper;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.impl.WorkerExecutor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.client.topic.TopicSubscription;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class Camunda7AdapterTest {

    private final ExternalTaskClient externalTaskClient =
            Mockito.mock(ExternalTaskClient.class);
    private final WorkerExecuteApi workerExecuteApi =
            Mockito.mock(WorkerExecuteApi.class);

    private final Camunda7PojoMapper mapper =
            Mockito.mock(Camunda7PojoMapper.class);

    private final Camunda7WorkerAdapter adapter =
            new Camunda7WorkerAdapter(this.externalTaskClient, this.workerExecuteApi, mapper);

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
