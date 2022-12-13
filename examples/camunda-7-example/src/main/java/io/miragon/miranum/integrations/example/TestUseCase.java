package io.miragon.miranum.integrations.example;

import io.miragon.miranum.connect.binder.domain.UseCase;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
public class TestUseCase {

    @UseCase(type = "test")
    public Output doSomething(final Input input) {
        log.info(input.toString());
        return new Output("output");
    }
}
