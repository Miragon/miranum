package io.miragon.miranum.connect.binder.adapter.camunda7;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.client.topic.TopicSubscription;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
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

    private final Camunda7Mapper camunda7Mapper =
            Mockito.mock(Camunda7Mapper.class);

    private final ExecuteMethodUseCase executeMethodUseCase =
            Mockito.mock(ExecuteMethodUseCase.class);

    private final Camunda7Adapter adapter =
            new Camunda7Adapter(this.externalTaskClient, this.camunda7Mapper, this.executeMethodUseCase);

    @Test
    void givenOneUseCase_thenExternalTaskClientSubscribesOnce() {
        final UseCaseInfo useCaseInfo = this.givenDefaultUseCase("defaultUseCase", 100L);
        final TopicSubscriptionBuilder builder = this.givenTopicSubscriptionBuilder();

        given(this.externalTaskClient.subscribe("defaultUseCase")).willReturn(builder);

        this.adapter.bind(useCaseInfo);

        then(this.externalTaskClient).should().subscribe("defaultUseCase");
        then(this.externalTaskClient).shouldHaveNoMoreInteractions();

        then(builder).should().open();
        then(builder).should().handler(any());
        then(builder).should().lockDuration(100L);
        then(builder).shouldHaveNoMoreInteractions();
    }


    @Test
    void givenDefaultUseCaseAndSuccessfullTask_thenEverythingGetsExecuted() {
        final ExternalTask externalTask = this.givenDefaultTask();
        final ExternalTaskService service = this.givenExternalTaskService();
        final UseCaseInfo useCaseInfo = this.givenDefaultUseCase("defaultUseCase", 100L);
        final Map<String, Object> result = Map.of("value", "test");
        given(this.executeMethodUseCase.execute(any())).willReturn(result);
        given(this.camunda7Mapper.mapOutput(any())).willReturn(result);

        this.adapter.execute(externalTask, service, useCaseInfo);

        then(service).should().complete(externalTask, null, result);
    }

    private UseCaseInfo givenDefaultUseCase(final String type, final Long lockDuration) {
        final UseCaseInfo useCaseInfo = Mockito.mock(UseCaseInfo.class);
        given(useCaseInfo.getType()).willReturn(type);
        given(useCaseInfo.getTimeout()).willReturn(lockDuration);
        return useCaseInfo;
    }

    private ExternalTask givenDefaultTask() {
        final ExternalTask externalTask = Mockito.mock(ExternalTask.class);
        return externalTask;
    }

    private ExternalTaskService givenExternalTaskService() {
        final ExternalTaskService externalTaskService = Mockito.mock(ExternalTaskService.class);
        return externalTaskService;
    }

    private TopicSubscriptionBuilder givenTopicSubscriptionBuilder() {
        final TopicSubscriptionBuilder builder = Mockito.mock(TopicSubscriptionBuilder.class);
        given(builder.handler(any())).willReturn(builder);
        given(builder.lockDuration(anyLong())).willReturn(builder);
        given(builder.open()).willReturn(Mockito.mock(TopicSubscription.class));
        return builder;
    }

}
