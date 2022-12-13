package io.miragon.miranum.connect.binder.adapter.camunda8;

import io.camunda.zeebe.client.ZeebeClient;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteUseCaseCommand;
import io.miragon.miranum.connect.binder.application.port.out.BindUseCasePort;
import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
class C8Adapter implements BindUseCasePort {

    private final ZeebeClient client;
    private final ExecuteMethodUseCase executeMethodUseCase;

    @Override
    public void bind(final UseCaseInfo useCaseInfo) {
        this.client
                .newWorker()
                .jobType(useCaseInfo.getType())
                .handler((client, job) -> {
                            try {
                                //1. map values
                                final Object value = job.getVariablesAsType(useCaseInfo.getInputType());
                                //2. execute method
                                final Object result = this.executeMethodUseCase.execute(new ExecuteUseCaseCommand(value, useCaseInfo));
                                //3. complete
                                client.newCompleteCommand(job.getKey()).variables(result).send().join();
                            } catch (final BusinessException exception) {
                                log.error("use case could not be executed", exception);
                                client.newThrowErrorCommand(job.getKey()).errorCode(exception.getCode()).send().join();
                            } catch (final Exception error) {
                                log.error("Something went wrong", error);
                                //todo retry cycle
                            }
                        }
                )
                .name(useCaseInfo.getType())
                .timeout(Duration.ofSeconds(10))
                .open();
    }

}
