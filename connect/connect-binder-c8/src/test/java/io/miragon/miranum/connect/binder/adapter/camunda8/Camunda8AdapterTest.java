package io.miragon.miranum.connect.binder.adapter.camunda8;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorkerBuilderStep1;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class Camunda8AdapterTest {

    private final ZeebeClient zeebeClient =
            Mockito.mock(ZeebeClient.class);

    private final ExecuteMethodUseCase executeMethodUseCase =
            Mockito.mock(ExecuteMethodUseCase.class);

    private final Camunda8Adapter adapter =
            new Camunda8Adapter(this.zeebeClient, this.executeMethodUseCase);

    @Test
    void givenOneUseCase_thenExternalTaskClientSubscribesOnce() {

        final UseCaseInfo useCaseInfo = this.givenDefaultUseCase("defaultUseCase", 100L);
        final JobWorkerBuilderStep1 step1 = Mockito.mock(JobWorkerBuilderStep1.class);
        final JobWorkerBuilderStep1.JobWorkerBuilderStep2 step2 = Mockito.mock(JobWorkerBuilderStep1.JobWorkerBuilderStep2.class);
        final JobWorkerBuilderStep1.JobWorkerBuilderStep3 step3 = Mockito.mock(JobWorkerBuilderStep1.JobWorkerBuilderStep3.class);
        given(step1.jobType("defaultUseCase")).willReturn(step2);
        given(step2.handler(any())).willReturn(step3);
        given(step3.name("defaultUseCase")).willReturn(step3);
        given(step3.timeout(100L)).willReturn(step3);
        given(this.zeebeClient.newWorker()).willReturn(step1);

        this.adapter.bind(useCaseInfo);

        then(step3).should().open();
    }


    @Test
    void givenDefaultUseCaseAndSuccessfullTask_thenEverythingGetsExecuted() {

    }

    private UseCaseInfo givenDefaultUseCase(final String type, final Long lockDuration) {
        final UseCaseInfo useCaseInfo = Mockito.mock(UseCaseInfo.class);
        given(useCaseInfo.getType()).willReturn(type);
        given(useCaseInfo.getTimeout()).willReturn(lockDuration);
        return useCaseInfo;
    }


}
