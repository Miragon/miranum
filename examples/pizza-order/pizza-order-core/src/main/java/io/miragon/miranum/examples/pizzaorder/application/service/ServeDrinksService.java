package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.ServeDrinksUseCase;
import io.miragon.miranum.examples.pizzaorder.application.port.in.OrderCommand;
import io.miragon.miranum.examples.pizzaorder.domain.Waiter;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@AllArgsConstructor
public class ServeDrinksService implements ServeDrinksUseCase {

    @Override
    @Worker(type = "serveDrinks")
    public OrderCommand serveDrinks(OrderCommand orderCommand) {
        var waiter = new Waiter();
        waiter.serveDrinks();
        return orderCommand;
    }
}
