package io.miragon.miranum.connect.binder.application.service;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodCommand;
import io.miragon.miranum.connect.binder.application.service.worker.DefaultWorker;
import io.miragon.miranum.connect.binder.application.service.worker.Input;
import io.miragon.miranum.connect.binder.application.service.worker.Output;
import io.miragon.miranum.connect.binder.domain.WorkerInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExecuteMethodServiceTest {

    private final ExecuteMethodService executeMethodService =
            new ExecuteMethodService(new ArrayList<>());

    @Test
    void givenDefaultUseCase_thenOutputIsReturned() throws NoSuchMethodException {
        final WorkerInfo useCaseInfo = this.givenDefaultUseCase();
        final Input input = new Input("test");

        final ExecuteMethodCommand command = new ExecuteMethodCommand(input, useCaseInfo);

        final Object result = this.executeMethodService.execute(command);

        assertEquals(Output.class, result.getClass());
    }

    @Test
    void givenVoidUseCase_thenNothingIsReturned() throws NoSuchMethodException {
        final WorkerInfo useCaseInfo = this.givenVoidUseCase();
        final Input input = new Input("test");

        final ExecuteMethodCommand command = new ExecuteMethodCommand(input, useCaseInfo);

        final Object result = this.executeMethodService.execute(command);

        assertNull(result);
    }

    private WorkerInfo givenDefaultUseCase() throws NoSuchMethodException {
        final DefaultWorker useCase = new DefaultWorker();
        return new WorkerInfo("test", 30000L, useCase, useCase.getClass().getMethod("doSomething", Input.class), Input.class, Output.class);
    }

    private WorkerInfo givenVoidUseCase() throws NoSuchMethodException {
        final DefaultWorker useCase = new DefaultWorker();
        return new WorkerInfo("test", 30000L, useCase, useCase.getClass().getMethod("doVoid", Input.class), Input.class, Void.class);
    }

}
