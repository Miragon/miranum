package io.miragon.miranum.connect.binder.application.service.usecase;

import io.miragon.miranum.connect.binder.domain.UseCase;

public class DefaultUseCase {

    @UseCase(type = "doSomething")
    public Output doSomething(final Input input) {
        return new Output("");
    }

    @UseCase(type = "doVoid")
    public void doVoid(final Input input) {
        //
    }

}
