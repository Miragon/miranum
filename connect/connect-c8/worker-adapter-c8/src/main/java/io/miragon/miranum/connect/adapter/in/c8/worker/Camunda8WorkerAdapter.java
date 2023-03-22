package io.miragon.miranum.connect.adapter.in.c8.worker;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.CompleteJobCommandStep1;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import io.miragon.miranum.connect.worker.impl.BindWorkerPort;
import io.miragon.miranum.connect.worker.impl.ExecuteMethodCommand;
import io.miragon.miranum.connect.worker.impl.MethodExecutor;
import io.miragon.miranum.connect.worker.impl.WorkerExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class Camunda8WorkerAdapter implements BindWorkerPort {

    private final ZeebeClient client;
    private final MethodExecutor methodExecutor;

    @Override
    public void bind(final WorkerExecutor workerExecutor) {
        this.client
                .newWorker()
                .jobType(workerExecutor.getType())
                .handler((client, job) -> this.execute(client, job, workerExecutor))
                .name(workerExecutor.getType())
                .timeout(workerExecutor.getTimeout())
                .open();
    }

    public void execute(final JobClient client, final ActivatedJob job, final WorkerExecutor workerExecutor) {
        try {
            //1. map values
            final Object value = job.getVariablesAsType(workerExecutor.getInputType());
            //2. execute method
            final Optional<Object> result = Optional.ofNullable(this.methodExecutor.execute(new ExecuteMethodCommand(value, workerExecutor)));

            final CompleteJobCommandStep1 cmd = client.newCompleteCommand(job.getKey());
            //3. add variables if result is not null
            result.ifPresent(cmd::variables);
            //4. complete
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
