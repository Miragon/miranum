package io.miragon.miranum.examples.process.application.service;

import io.miragon.miranum.examples.process.application.port.in.DeliverPizzaCommand;
import io.miragon.miranum.examples.process.application.port.in.DeliverPizzaUseCase;
import io.miragon.miranum.examples.process.domain.Waiter;

public class DeliverPizzaService implements DeliverPizzaUseCase {

    @Override
    public void deliverPizza(DeliverPizzaCommand deliverPizzaCommand) {
        var waiter = new Waiter();
        waiter.deliverPizza();
    }
}
