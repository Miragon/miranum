package io.miragon.miranum.connect.binder.application.service;

import io.miragon.miranum.connect.binder.application.port.in.InitalizeUseCasesCommand;
import io.miragon.miranum.connect.binder.application.port.in.InitializeUseCase;
import io.miragon.miranum.connect.binder.application.port.out.BindUseCasePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InitalizeUseCasesService implements InitializeUseCase {

    private final BindUseCasePort bindUseCasePort;

    @Override
    public void initalize(final InitalizeUseCasesCommand initalizeUseCasesCommand) {
        initalizeUseCasesCommand.getUseCases().forEach(this.bindUseCasePort::bind);
    }
}
