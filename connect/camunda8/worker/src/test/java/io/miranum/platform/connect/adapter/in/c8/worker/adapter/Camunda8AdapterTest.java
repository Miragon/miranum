package io.miranum.platform.connect.adapter.in.c8.worker.adapter;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.command.CompleteJobCommandStep1;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.miranum.platform.connect.worker.api.WorkerExecuteApi;
import io.miranum.platform.connect.worker.impl.WorkerExecutor;
import io.miranum.platform.connect.adapter.in.c8.worker.Camunda8WorkerAdapter;
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
    private final WorkerExecuteApi workerExecuteApi =
            Mockito.mock(WorkerExecuteApi.class);

    private final Camunda8WorkerAdapter adapter =
            new Camunda8WorkerAdapter(this.zeebeClient, this.workerExecuteApi);

    @Test
    void givenDefaultUseCaseAndSuccessfulTask_thenEverythingGetsExecuted() {
        final ActivatedJob job = this.givenDefaultJob(1L);
        final WorkerExecutor executor = this.givenDefaultExecutor("defaultUseCase", 100L);
        final Map<String, Object> result = Map.of("value", "test");
        final JobClient client = this.givenDefaultClient(result);

        given(this.workerExecuteApi.execute(any(), any())).willReturn(result);

        this.adapter.execute(client, job, executor);

        then(client).should().newCompleteCommand(1L);
    }

    @Test
    void givenDefaultUseCaseWithNullInputType_thenShouldNotThrowAnyException() {
        final ActivatedJob job = this.givenDefaultJob(1L);
        final WorkerExecutor executor = this.givenDefaultExecutor("defaultUseCase", 100L);
        given(executor.getInputType()).willReturn(null);
        final Map<String, Object> result = Map.of("value", "test");
        final JobClient client = this.givenDefaultClient(result);

        given(this.workerExecuteApi.execute(any(), any())).willReturn(result);

        this.adapter.execute(client, job, executor);

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

    private WorkerExecutor givenDefaultExecutor(final String type, final Long lockDuration) {
        final WorkerExecutor useCaseInfo = Mockito.mock(WorkerExecutor.class);
        given(useCaseInfo.getType()).willReturn(type);
        given(useCaseInfo.getTimeout()).willReturn(lockDuration);
        return useCaseInfo;
    }
}