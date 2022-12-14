package io.miragon.miranum.connect.binder.adapter.camunda8;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.CompleteJobCommandStep1;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteUseCaseCommand;
import io.miragon.miranum.connect.binder.application.port.out.BindUseCasePort;
import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.TechnicalException;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
class Camunda8Adapter implements BindUseCasePort {

    private final ZeebeClient client;
    private final ExecuteMethodUseCase executeMethodUseCase;

    @Override
    public void bind(final UseCaseInfo useCaseInfo) {
        this.client
                .newWorker()
                .jobType(useCaseInfo.getType())
                .handler((client, job) -> this.execute(client, job, useCaseInfo))
                .name(useCaseInfo.getType())
                .timeout(useCaseInfo.getTimeout())
                .open();
    }

    public void execute(final JobClient client, final ActivatedJob job, final UseCaseInfo useCaseInfo) {
        try {
            //1. map values
            final Object value = job.getVariablesAsType(useCaseInfo.getInputType());
            //2. execute method
            final Optional<Object> result = Optional.ofNullable(this.executeMethodUseCase.execute(new ExecuteUseCaseCommand(value, useCaseInfo)));

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
