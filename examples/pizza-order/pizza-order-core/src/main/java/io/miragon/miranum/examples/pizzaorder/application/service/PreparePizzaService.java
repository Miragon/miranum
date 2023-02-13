package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.PreparePizzaCommand;
import io.miragon.miranum.examples.pizzaorder.application.port.in.PreparePizzaUseCase;
import io.miragon.miranum.examples.pizzaorder.domain.Cook;

public class PreparePizzaService implements PreparePizzaUseCase {

    @Override
    @Worker(type = "preparePizza")
    public void preparePizza(PreparePizzaCommand preparePizzaCommand) {
        var cook = new Cook();
        cook.makePizza();
    }
}
