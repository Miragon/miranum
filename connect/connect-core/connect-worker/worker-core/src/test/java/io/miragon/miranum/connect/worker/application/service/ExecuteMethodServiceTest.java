package io.miragon.miranum.connect.worker.application.service;

import io.miragon.miranum.connect.worker.application.port.in.ExecuteMethodCommand;
import io.miragon.miranum.connect.worker.domain.BusinessException;
import io.miragon.miranum.connect.worker.domain.TechnicalException;
import io.miragon.miranum.connect.worker.domain.WorkerInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class ExecuteMethodServiceTest {

    private final ExecuteMethodService executeMethodService = new ExecuteMethodService(List.of());

    @Test
    void executeMethod_throwsTechnicalException_whenTechnicalExceptionIsThrownByApplication() throws NoSuchMethodException {
        var command = new ExecuteMethodCommand(null, getMockedWorker(new TechnicalException("-")));
        assertThrows(TechnicalException.class, () -> this.executeMethodService.execute(command));
    }

    @Test
    void executeMethod_throwsBusinessException_whenBusinessExceptionIsThrownByApplication() throws NoSuchMethodException {
        var command = new ExecuteMethodCommand(null, getMockedWorker(new BusinessException("-", "-")));
        assertThrows(BusinessException.class, () -> this.executeMethodService.execute(command));
    }

    private WorkerInfo getMockedWorker(Exception exception) throws NoSuchMethodException {

        class MockService {
            public String mockServiceMethod(Object ignored) {
                throw (RuntimeException) exception;
            }
        }
        final WorkerInfo workerInfo = Mockito.mock(WorkerInfo.class);
        given(workerInfo.getMethod()).willReturn(MockService.class.getMethod("mockServiceMethod", Object.class));
        given(workerInfo.getInstance()).willReturn(new MockService());
        return workerInfo;
    }
}
