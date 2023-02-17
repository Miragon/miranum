package io.miragon.miranum.examples.pizzaorder.kitchen.adapter.in.miranum;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.examples.pizzaorder.kitchen.application.port.in.PreparePizzaCommand;
import io.miragon.miranum.examples.pizzaorder.kitchen.application.port.in.PreparePizzaUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MiranumKitchenAdapter {

    private final PreparePizzaUseCase preparePizzaUseCase;

    @Worker(type = "preparePizza")
    public void preparePizza(PreparePizzaCommand preparePizzaCommand) {
        preparePizzaUseCase.preparePizza(preparePizzaCommand);
    }
}
