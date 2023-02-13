package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.DeliverDrinksCommand;
import io.miragon.miranum.examples.pizzaorder.application.port.in.DeliverDrinksUseCase;
import io.miragon.miranum.examples.pizzaorder.domain.Waiter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeliverDrinksService implements DeliverDrinksUseCase {

    @Override
    @Worker(type = "deliverDrinks")
    public void deliverDrinks(DeliverDrinksCommand deliverDrinksCommand) {
        var waiter = new Waiter();
        waiter.deliverDrinks();
    }
}
