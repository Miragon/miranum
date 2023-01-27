package io.miragon.miranum.connect.adapter.in.c8.worker.adapter;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.command.CompleteJobCommandStep1;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobWorkerBuilderStep1;
import io.miragon.miranum.connect.adapter.in.c8.worker.Camunda8WorkerAdapter;
import io.miragon.miranum.connect.worker.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.worker.domain.WorkerInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class Camunda8AdapterTest {

    private final ZeebeClient zeebeClient =
            Mockito.mock(ZeebeClient.class);

    private final ExecuteMethodUseCase executeMethodUseCase =
            Mockito.mock(ExecuteMethodUseCase.class);

    private final Camunda8WorkerAdapter adapter =
            new Camunda8WorkerAdapter(this.zeebeClient, this.executeMethodUseCase);

    @Test
    void givenOneUseCase_thenExternalTaskClientSubscribesOnce() {

        final WorkerInfo useCaseInfo = this.givenDefaultUseCase("defaultUseCase", 100L);
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
        final ActivatedJob job = this.givenDefaultJob(1L);
        final WorkerInfo useCaseInfo = this.givenDefaultUseCase("defaultUseCase", 100L);
        final Map<String, Object> result = Map.of("value", "test");
        final JobClient client = this.givenDefaultClient(result);
        given(this.executeMethodUseCase.execute(any())).willReturn(result);

        this.adapter.execute(client, job, useCaseInfo);

        then(client).should().newCompleteCommand(1L);
    }

    private ActivatedJob givenDefaultJob(final Long jobKey) {
        final ActivatedJob job = Mockito.mock(ActivatedJob.class);
        given(job.getKey()).willReturn(jobKey);
        return job;
    }

    private JobClient givenDefaultClient(final Object result) {
        final JobClient client = Mockito.mock(JobClient.class);
        final CompleteJobCommandStep1 step1 = Mockito.mock(CompleteJobCommandStep1.class);
        given(client.newCompleteCommand(anyLong())).willReturn(step1);
        given(step1.variables(result)).willReturn(step1);
        given(step1.send()).willReturn(Mockito.mock(ZeebeFuture.class));
        return client;
    }

    private WorkerInfo givenDefaultUseCase(final String type, final Long lockDuration) {
        final WorkerInfo useCaseInfo = Mockito.mock(WorkerInfo.class);
        given(useCaseInfo.getType()).willReturn(type);
        given(useCaseInfo.getTimeout()).willReturn(lockDuration);
        return useCaseInfo;
    }


}
