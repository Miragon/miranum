package io.miranum.platform.connect.adapter.in.c8.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.CompleteJobCommandStep1;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.miranum.platform.connect.worker.api.BusinessException;
import io.miranum.platform.connect.worker.api.TechnicalException;
import io.miranum.platform.connect.worker.api.WorkerExecuteApi;
import io.miranum.platform.connect.worker.api.WorkerSubscription;
import io.miranum.platform.connect.worker.impl.WorkerExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class Camunda8WorkerAdapter implements WorkerSubscription {

    private final ZeebeClient client;
    private final WorkerExecuteApi workerExecuteApi;

    @Override
    public void subscribe(final WorkerExecutor executor) {
        this.client
                .newWorker()
                .jobType(executor.getType())
                .handler((client, job) -> this.execute(client, job, executor))
                .name(executor.getType())
                .timeout(executor.getTimeout())
                .open();
    }

    public void execute(final JobClient client, final ActivatedJob job, final WorkerExecutor workerExecutor) {
        try {
            var input = workerExecutor.hasInputType() ? job.getVariablesAsType(workerExecutor.getInputType()) : null;
            final Map<String, Object> result = this.workerExecuteApi.execute(workerExecutor, input);
            final CompleteJobCommandStep1 cmd = client.newCompleteCommand(job.getKey());
            cmd.variables(result);
            cmd.send().join();
        } catch (final BusinessException exception) {
            log.error("business error detected", exception);
            client.newThrowErrorCommand(job.getKey()).errorCode(exception.getCode()).send().join();
        } catch (final TechnicalException exception) {
            log.error("technical error detected", exception);
            client.newFailCommand(job.getKey()).retries(0).send().join();
        } catch (final Exception exception) {
            log.error("general exception detected", exception);
            client.newFailCommand(job.getKey()).retries(job.getRetries() - 1).send().join();
        }
    }

}