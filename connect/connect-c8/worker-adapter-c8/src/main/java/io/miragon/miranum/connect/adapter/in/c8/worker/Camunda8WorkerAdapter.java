package io.miragon.miranum.connect.adapter.in.c8.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.CompleteJobCommandStep1;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.impl.WorkerExecutor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class Camunda8WorkerAdapter {

    private final ZeebeClient client;
    private final WorkerExecuteApi workerExecuteApi;

    @PostConstruct
    public void init() {
        // external task client subscribe to each available worker type
        this.workerExecuteApi.availableWorkerExecutors()
                .forEach(workerExecutor -> {
                    this.client
                            .newWorker()
                            .jobType(workerExecutor.getType())
                            .handler((client, job) -> this.execute(client, job, workerExecutor))
                            .name(workerExecutor.getType())
                            .timeout(workerExecutor.getTimeout())
                            .open();
                });
    }

    public void execute(final JobClient client, final ActivatedJob job, final WorkerExecutor workerExecutor) {
        try {
            final Map<String, Object> result = this.workerExecuteApi.execute(
                    workerExecutor.getType(), job.getVariablesAsType(workerExecutor.getInputType())
            );
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
