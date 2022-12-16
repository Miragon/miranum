package io.miragon.miranum.connect.binder.adapter.camunda7.worker;

import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.worker.application.port.out.BindWorkerPort;
import io.miragon.miranum.connect.binder.worker.domain.BusinessException;
import io.miragon.miranum.connect.binder.worker.domain.TechnicalException;
import io.miragon.miranum.connect.binder.worker.domain.WorkerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Camunda7EmbeddedAdapter implements BindWorkerPort {

    private final ExecuteMethodUseCase executeMethodUseCase;

    @Override
    public void bind(final WorkerInfo useCaseInfo) {

    }

    public void execute(final WorkerInfo useCaseInfo) {
        try {

        } catch (final BusinessException exception) {
            log.error("business error detected", exception);
        } catch (final TechnicalException exception) {
            log.error("technical error detected", exception);
        } catch (final Exception exception) {
            log.error("general exception detected", exception);
        }
    }

}
