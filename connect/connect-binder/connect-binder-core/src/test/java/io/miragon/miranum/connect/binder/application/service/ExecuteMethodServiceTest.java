package io.miragon.miranum.connect.binder.application.service;

import io.miragon.miranum.connect.binder.application.port.in.ExecuteUseCaseCommand;
import io.miragon.miranum.connect.binder.application.service.usecase.DefaultUseCase;
import io.miragon.miranum.connect.binder.application.service.usecase.Input;
import io.miragon.miranum.connect.binder.application.service.usecase.Output;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExecuteMethodServiceTest {

    private final ExecuteMethodService executeMethodService =
            new ExecuteMethodService(new ArrayList<>());

    @Test
    void givenDefaultUseCase_thenOutputIsReturned() throws NoSuchMethodException {

        final UseCaseInfo useCaseInfo = this.givenDefaultUseCase();
        final Input input = new Input("test");

        final ExecuteUseCaseCommand command = new ExecuteUseCaseCommand(input, useCaseInfo);

        final Object result = this.executeMethodService.execute(command);

        assertEquals(Output.class, result.getClass());
    }

    private UseCaseInfo givenDefaultUseCase() throws NoSuchMethodException {
        final DefaultUseCase useCase = new DefaultUseCase();
        return new UseCaseInfo("test", 30000L, useCase, useCase.getClass().getMethod("doSomething", Input.class), Input.class, Output.class);
    }

}
