package io.miragon.miranum.examples.pizzaorder.waiter.adapter.in.miranum;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiranumWaiterAdapter {

    private final ServeDrinksUseCase serveDrinksUseCase;
    private final ServeFoodUseCase serveFoodUseCase;
    private final IssueCheckUseCase issueCheckUseCase;

    @Worker(type = "serveDrinks")
    public void serveDrinks(ServeDrinksCommand serveDrinksCommand) {
        serveDrinksUseCase.serveDrinks(serveDrinksCommand);
    }

    @Worker(type = "serveFood")
    public void serveFood(ServeFoodCommand serveFoodCommand) {
        serveFoodUseCase.serveFood(serveFoodCommand);
    }

    @Worker(type = "issueCheck")
    public void issueCheck(IssueCheckCommand issueCheckCommand) {
        issueCheckUseCase.issueCheck(issueCheckCommand);
    }
}