package io.miragon.miranum.connect.worker.impl;

import io.miragon.miranum.connect.worker.api.BusinessException;
import io.miragon.miranum.connect.worker.api.TechnicalException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class ExecuteMethodServiceTest {

    private final MethodExecutor executeMethodService = new MethodExecutor(List.of());

    @Test
    void executeMethod_throwsTechnicalException_whenTechnicalExceptionIsThrownByApplication() throws NoSuchMethodException {
        final var command = new ExecuteMethodCommand(null, this.getMockedWorker(new TechnicalException("-")));
        assertThrows(TechnicalException.class, () -> this.executeMethodService.execute(command));
    }

    @Test
    void executeMethod_throwsBusinessException_whenBusinessExceptionIsThrownByApplication() throws NoSuchMethodException {
        final var command = new ExecuteMethodCommand(null, this.getMockedWorker(new BusinessException("-", "-")));
        assertThrows(BusinessException.class, () -> this.executeMethodService.execute(command));
    }

    private WorkerInfo getMockedWorker(final Exception exception) throws NoSuchMethodException {

        class MockService {
            public String mockServiceMethod(final Object ignored) {
                throw (RuntimeException) exception;
            }
        }
        final WorkerInfo workerInfo = Mockito.mock(WorkerInfo.class);
        given(workerInfo.getMethod()).willReturn(MockService.class.getMethod("mockServiceMethod", Object.class));
        given(workerInfo.getInstance()).willReturn(new MockService());
        return workerInfo;
    }
}
