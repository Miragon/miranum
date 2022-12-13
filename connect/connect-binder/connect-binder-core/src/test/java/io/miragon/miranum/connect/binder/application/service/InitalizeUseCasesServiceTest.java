package io.miragon.miranum.connect.binder.application.service;

import io.miragon.miranum.connect.binder.application.port.in.InitalizeUseCasesCommand;
import io.miragon.miranum.connect.binder.application.port.out.BindUseCasePort;
import io.miragon.miranum.connect.binder.application.service.usecase.DefaultUseCase;
import io.miragon.miranum.connect.binder.application.service.usecase.Input;
import io.miragon.miranum.connect.binder.application.service.usecase.Output;
import io.miragon.miranum.connect.binder.domain.UseCaseInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;

class InitalizeUseCasesServiceTest {


    private final BindUseCasePort bindUseCasePort =
            Mockito.mock(BindUseCasePort.class);

    private final InitalizeUseCasesService initalizeUseCasesService =
            new InitalizeUseCasesService(this.bindUseCasePort);

    @Test
    void givenDefaultUseCase_thenBinderIsCalledOnce() throws NoSuchMethodException {

        final List<UseCaseInfo> useCaseInfos = this.givenDefaultUseCaseList();

        final InitalizeUseCasesCommand command = new InitalizeUseCasesCommand(useCaseInfos);

        this.initalizeUseCasesService.initalize(command);

        then(this.bindUseCasePort).should().bind(eq(useCaseInfos.get(0)));
        then(this.bindUseCasePort).shouldHaveNoMoreInteractions();

    }

    private List<UseCaseInfo> givenDefaultUseCaseList() throws NoSuchMethodException {
        final DefaultUseCase useCase = new DefaultUseCase();
        final UseCaseInfo useCaseInfo = new UseCaseInfo("test", useCase, useCase.getClass().getMethod("doSomething", Input.class), Input.class, Output.class);
        return List.of(useCaseInfo);
    }

}
