package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.OrderCommand;
import io.miragon.miranum.examples.pizzaorder.application.port.in.ServePizzaUseCase;
import io.miragon.miranum.examples.pizzaorder.domain.Waiter;

public class ServePizzaService implements ServePizzaUseCase {
    @Override
    @Worker(type = "servePizza")
    public OrderCommand servePizza(OrderCommand orderCommand) {
        var waiter = new Waiter();
        waiter.servePizza();
        return orderCommand;
    }
}