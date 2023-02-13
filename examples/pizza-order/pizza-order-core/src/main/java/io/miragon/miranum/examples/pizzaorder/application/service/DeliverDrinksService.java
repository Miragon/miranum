package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.DeliverDrinksUseCase;
import io.miragon.miranum.examples.pizzaorder.application.port.in.OrderCommand;
import io.miragon.miranum.examples.pizzaorder.domain.Waiter;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@AllArgsConstructor
public class DeliverDrinksService implements DeliverDrinksUseCase {

    @Override
    @Worker(type = "deliverDrinks")
    public OrderCommand deliverDrinks(OrderCommand orderCommand) {
        var waiter = new Waiter();
        waiter.deliverDrinks();
        return orderCommand;
    }
}
