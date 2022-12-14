package io.miragon.miranum.connect.binder.adapter.camunda7;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscription;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

    private final C7Adapter adapter =
            new C7Adapter(this.externalTaskClient, this.camunda7Mapper, this.executeMethodUseCase);

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

    private UseCaseInfo givenDefaultUseCase(final String type, final Long lockDuration) {
        final UseCaseInfo useCaseInfo = Mockito.mock(UseCaseInfo.class);
        given(useCaseInfo.getType()).willReturn(type);
        given(useCaseInfo.getTimeout()).willReturn(lockDuration);
        return useCaseInfo;
    }

    private TopicSubscriptionBuilder givenTopicSubscriptionBuilder() {
        final TopicSubscriptionBuilder builder = Mockito.mock(TopicSubscriptionBuilder.class);
        given(builder.handler(any())).willReturn(builder);
        given(builder.lockDuration(anyLong())).willReturn(builder);
        given(builder.open()).willReturn(Mockito.mock(TopicSubscription.class));
        return builder;
    }

}
