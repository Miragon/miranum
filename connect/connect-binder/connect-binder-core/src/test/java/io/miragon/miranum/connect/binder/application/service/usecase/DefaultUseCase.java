package io.miragon.miranum.connect.binder.application.service.usecase;

import io.miragon.miranum.connect.binder.domain.UseCase;

public class DefaultUseCase {

    @UseCase(type = "test")
    public Output doSomething(final Input input) {
        return new Output("");
    }

}
